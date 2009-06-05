package org.xmldsl.schema;

import org.xmldsl.common.EnumUtils;
import org.xmldsl.schema.annotation.Constraint;
import org.xmldsl.schema.formats.ConstraintFormats;
import org.xmldsl.formats.Formats;
import org.xmldsl.formats.TypeFormats;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
@SuppressWarnings({"unchecked"})
public class XmlAttributeFormat<E extends Enum<E>, T> implements Formats<T> {
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
