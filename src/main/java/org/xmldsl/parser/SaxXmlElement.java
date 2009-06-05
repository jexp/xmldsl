package org.xmldsl.parser;

import org.xml.sax.Attributes;

import java.util.Collection;
import java.util.EnumMap;

/**
 * @author mcr
 * @since 27.04.2009
 */
public class SaxXmlElement<E extends Enum<E>> extends AbstractXmlElement<E> {
    private final String uri;
    private final String name;
    private final Attributes attributes;

    public SaxXmlElement(final Class<E> type, final String uri, final String name, final Attributes attributes) {
        super(type);
        this.uri = uri;
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String getValue(final E attribute) {
        return attributes.getValue(uri, attribute.name());
    }

    public EnumMap<E, String> getAttributes() {
        final int attributeCount = attributes.getLength();
        final EnumMap<E, String> result = new EnumMap<E, String>(getType());
        for (int i = 0; i < attributeCount; i++) {
            result.put(enumByName(attributes.getLocalName(i)), attributes.getValue(i));
        }
        return result;
    }

    public <T extends Enum<T>> Collection<XmlElement<T>> getChildren(final Class<T> childType) {
        throw new UnsupportedOperationException("getChildren not possible with Sax");
    }

    public <T extends Enum<T>> Collection<XmlElement<T>> getChildren() {
        throw new UnsupportedOperationException("getChildren not possible with Sax");
    }
}
