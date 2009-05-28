package de.jexp.xml.builder;

import java.util.Map;

/**
 * @author mcr
 * @since 12.05.2009
 */
public abstract class AbstractXmlBuilder implements XmlBuilder {
    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final Map<E, ?> attributes) {
        final Tag<E> tag = tag(tagEnum);
        tag.attr(attributes);
        return tag;
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final E name, final Object value) {
        final Tag<E> tag = tag(tagEnum);
        tag.attr(name, value);
        return tag;
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final E name, final Object value, final E name2, final Object value2) {
        return tag(tagEnum).attr(name, value).attr(name2, value2);
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum, final E name, final Object value, final E name2, final Object value2, final E name3, final Object value3) {
        return tag(tagEnum).attr(name, value).attr(name2, value2).attr(name3, value3);
    }

    protected void childExistInParent(final Tag<?> parent, final Tag<?> child) {
        if (!parent.getTagType().equals(child.getTagType().getDeclaringClass())) {
            throw new IllegalArgumentException(String.format("%s is not subtag of %s", child, parent));
        }
    }
}
