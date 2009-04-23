package de.jexp.xml;

import com.sun.tools.jdi.EventSetImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.Format;
import java.util.Date;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class Tag<E extends Enum<E>> {
    XmlBuilder builder;
    private String name;

    public Tag(final Class<E> tagEnum, final XmlBuilder builder) {
        this.name = tagEnum.getSimpleName();
        this.builder = builder;
    }

    public Tag<E> attr(final E attr, final Object value) {
        if (value != null)
            builder.addAttribute(this, attr.name(), value.toString());
        return this;
    }

    public Tag<E> attr(final E attr, final Format format, final Object value) {
        if (value != null)
            builder.addAttribute(this, attr.name(), format.format(value));
        return this;
    }

    public Tag<E> attr(final E attr, final String value) {
        builder.addAttribute(this, attr.name(), value);
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

    public Tag<E> add(final Tag<?>... children) {
        for (final Tag<?> child : children) {
            builder.addChild(this, child);
        }
        builder.endTags(this);
        return this;
    }

    public Tag<E> text(final String text) {
        builder.addText(this, text);
        return this;
    }

    public Tag<E> tags() {
        builder.startTags(this);
        return this;
    }

    @Override
    public String toString() {
        return "<"+getName()+">";
    }
}