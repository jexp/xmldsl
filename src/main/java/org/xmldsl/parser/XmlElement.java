package org.xmldsl.parser;

import org.jdom.Element;
import org.xml.sax.Attributes;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;

/**
 * @author mcr
 * @since 27.04.2009
 */
public interface XmlElement<E extends Enum<E>> {
    String getName();

    String getValue(E attribute);

    String getValue(E attribute, String defaultValue);

    Integer getInteger(E attribute);

    Date getDate(E attribute, String... format);

    BigDecimal getBigDecimal(E attribute);

    Collection<E> getAttributeNames();

    Class<E> getType();

    EnumMap<E, String> getAttributes();

    <T extends Enum<T>> Collection<XmlElement<T>> getChildren(Class<T> childType);

    <T extends Enum<T>> Collection<XmlElement<T>> getChildren();

    <C extends Enum<C>> Collection<XmlElement<C>> getChildren(final Class<C> childElementType, final int depth);

    <C extends Enum<C>> void handleChildren(Class<C> childElementType, XmlElementHandler<C> handler, final int depth);

    <C extends Enum<C>> void handleChildren(Class<C> childElementType, XmlElementHandler<C> handler);

    <T> T get(E attribute);

    class Create {
        public static <E extends Enum<E>> XmlElement<E> from(final Class<E> type, final Element element) {
            return new JdomXmlElement<E>(type, element);
        }

        public static <E extends Enum<E>> XmlElement<E> from(final Class<E> type, final String xml) {
            return new JdomXmlElement<E>(type, xml);
        }

        public static <E extends Enum<E>> XmlElement<E> from(final Class<E> type, final String uri, final String name, final Attributes attributes) {
            return new SaxXmlElement<E>(type, uri, name, attributes);
        }
    }

    class Equals {
        public static <E extends Enum<E>> boolean name(final Class<E> enumType, final String name) {
            return enumType.getSimpleName().equalsIgnoreCase(name);
        }
    }
}