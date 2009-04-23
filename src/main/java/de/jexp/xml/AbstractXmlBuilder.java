package de.jexp.xml;

import java.util.Map;

/**
 * @author Michael Hunger
 * @since 23.04.2009
 */
public abstract class AbstractXmlBuilder implements XmlBuilder {
    public Object getDelegate(final Tag<?> tag) {
        return null;
    }

    public void addAttribute(final Tag<?> tag, final String name, final String value) {

    }

    public void startTags(Tag<?> tag) {
    }

    public void endTags(Tag<?> tag) {
    }

    public void addText(final Tag<?> tag, final String text) {

    }

    public String toXml(final Tag<?> tag) {
        return null;
    }

    public void addChild(final Tag<?> parent, final Tag<?> child) {

    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum) {
        return new Tag<E>(tagEnum, this);
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final E attr, final Object value) {
        return tag(tagEnum).attr(attr, value);
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final E attr1, final Object value1, final E attr2, final Object value2) {
        return tag(tagEnum).attr(attr1, value1).attr(attr2, value2);
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final Map<E, Object> attr) {
        final Tag<E> tag = tag(tagEnum);
        for (final Map.Entry<E, Object> entry : attr.entrySet()) {
            tag.attr(entry.getKey(), entry.getValue());
        }
        return tag;
    }

}
