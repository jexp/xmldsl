package org.xmldsl.schema;

import org.xmldsl.formats.Formats;

import java.util.*;

/**
 * @author Michael Hunger
 * @since 27.05.2009
 */
@SuppressWarnings({"unchecked"})
public class XmlFormat<E extends Enum<E>> {
    private final Map<E, XmlAttributeFormat<E, ?>> attributeFormats;

    public XmlFormat(final Class<E> enumType) {
        attributeFormats = new EnumMap<E, XmlAttributeFormat<E, ?>>(enumType);
        for (final E attribute : enumType.getEnumConstants()) {
            attributeFormats.put(attribute, new XmlAttributeFormat<E, Object>(attribute));
        }
    }

    public <T> T parse(final E attribute, final String value) {
        return (T) parseObject(attribute, value).value;
    }

    public <T> Formats.ParseResult<T> parseObject(final E attribute, final String value) {
        return (Formats.ParseResult<T>) attributeFormats.get(attribute).parseObject(value);
    }

    public <T> String format(final E attribute, final T value) {
        final XmlAttributeFormat<E, T> format = (XmlAttributeFormat<E, T>) attributeFormats.get(attribute);
        return format.format(value);
    }

}
