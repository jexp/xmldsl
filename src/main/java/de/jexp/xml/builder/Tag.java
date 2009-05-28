package de.jexp.xml.builder;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import de.jexp.xml.common.Props;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class Tag<E extends Enum<E>> {
    XmlBuilder builder;
    private final String name;
    private final Class<E> tagType;

    public Tag(final Class<E> tagType, final XmlBuilder builder) {
        this.tagType = tagType;
        this.name = makeTagName(tagType);
        this.builder = builder;
    }

    public Class<E> getTagType() {
        return tagType;
    }

    protected String makeTagName(final Class<E> tagEnum) {
        return tagEnum.getSimpleName();
    }

    public Tag<E> attr(final E attr, final Object value) {
        if (value != null)
            builder.addAttribute(this, makeName(attr), value.toString());
        return this;
    }

    protected String makeName(final E attr) {
        return attr.name();
    }

    public Tag<E> attr(final E attr, final Format format, final Object value) {
        if (value != null)
            builder.addAttribute(this, makeName(attr), format.format(value));
        return this;
    }

    public Tag<E> attr(final E attr, final String value) {
        if (value != null)
            builder.addAttribute(this, makeName(attr), value);
        return this;
    }

    public Tag<E> attr(final Map<E, ?> attribs) {
        for (final Map.Entry<E, ?> attr : attribs.entrySet()) {
            attr(attr.getKey(), attr.getValue());
        }
        return this;
    }

    public Tag<E> attrExcept(final Props<E> attribs, final E... except) {
        return attrExcept(attribs.getProps(), except);
    }

    public Tag<E> attrExcept(final Map<E, ?> attribs, final E... except) {
        final EnumSet<E> exceptSet = EnumSet.of(except[0], except);
        for (final Map.Entry<E, ?> attr : attribs.entrySet()) {
            if (exceptSet.contains(attr.getKey())) continue;
            attr(attr.getKey(), attr.getValue());
        }
        return this;
    }

    public Tag<E> attr(final E attr, final Date value) {
        return attr(attr, DateFormat.getDateTimeInstance().format(value));
    }

    public Tag<E> attr(final E attr, final String format, final Date value) {
        return attr(attr, new SimpleDateFormat(format).format(value));
    }

    public Object getDelegate() {
        return builder.getDelegate(this);
    }

    public String getName() {
        return name;
    }

    public String toXml() {
        return builder.toXml(this);
    }

    public <T extends Enum<T>> Tag<E> tags(final Tag<T>... children) {
        return tags(Arrays.asList(children));
    }

    public <T extends Enum<T>> Tag<E> tags(final Iterable<Tag<T>> children) {
        for (final Tag<?> child : children) {
            builder.addChild(this, child);
        }
        return this;
    }

    public <F, T extends Enum<T>> Tag<E> tags(final Iterable<F> values, final TagMaker<F, T> tagMaker) {
        final Collection<Tag<T>> result = new LinkedList<Tag<T>>();
        for (final F value : values) {
            result.add(tagMaker.makeTag(value));
        }
        return tags(result);
    }

    public Tag<E> text(final String text) {
        builder.addText(this, text);
        return this;
    }

    public Tag<E> end() {
        builder.endTag(this);
        return this;
    }

    @Override
    public String toString() {
        return String.format("<%s/>", getName());
    }
}