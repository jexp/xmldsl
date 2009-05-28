package de.jexp.xml.dtd;

import de.jexp.xml.common.EnumUtils;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
@SuppressWarnings({"unchecked"})
class XmlAttributeFormat<E extends Enum<E>, T> implements Formats<T> {
    private final E attribute;
    private final Formats<T> format;

    public XmlAttributeFormat(final E attribute) {
        this.attribute = attribute;
        final Constraint constraint = EnumUtils.getEnumAnnotation(attribute, Constraint.class);
        if (constraint == null) {
            this.format = new TypeFormats<T>((Class<T>) String.class);
        } else {
            this.format = new ConstraintFormats<T>(constraint);
        }
    }

    public E getAttribute() {
        return attribute;
    }

    public ParseResult<T> parseObject(final String value) {
        return format.parseObject(value);
    }

    public String format(final T value) {
        return format.format(value);
    }
}
