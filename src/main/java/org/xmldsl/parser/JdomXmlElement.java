package org.xmldsl.parser;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;

/**
 * @author mcr
 * @since 27.04.2009
 */
public class JdomXmlElement<E extends Enum<E>> extends AbstractXmlElement<E> {
    final Element element;

    public JdomXmlElement(final Class<E> type, final Element element) {
        super(type);
        this.element = element;
    }

    public JdomXmlElement(final Class<E> type, final String xml) {
        this(type, getRootElement(xml));
    }

    private static Element getRootElement(final String xml) {
        try {
            final SAXBuilder builder = new SAXBuilder();
            final Document document = builder.build(new StringReader(xml));
            return document.getRootElement();
        } catch (JDOMException e) {
            throw new RuntimeException("document invalid", e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error loading the document", e);
        }
    }

    public String getName() {
        return element.getName();
    }

    @SuppressWarnings({"unchecked"})
    private Attribute getAttribute(final Enum enumAttribute) {
        final String enumName = enumAttribute.name();
        for (final Attribute attribute : (Iterable<Attribute>) element.getAttributes()) {
            final String attributeName = attribute.getName();
            if (enumName.equalsIgnoreCase(attributeName)) return attribute;
        }
        return null;
    }

    public String getValue(final E attribute) {
        final Attribute jdomAttribute = getAttribute(attribute);
        if (jdomAttribute == null) return null;
        return jdomAttribute.getValue();
    }

    @SuppressWarnings({"unchecked"})
    public EnumMap<E, String> getAttributes() {
        final EnumMap<E, String> attributes = new EnumMap<E, String>(getType());
        for (final Attribute attribute : (Iterable<Attribute>) element.getAttributes()) {
            attributes.put(enumByName(attribute.getName()), attribute.getValue());
        }
        return attributes;
    }

    public <T extends Enum<T>> Collection<XmlElement<T>> getChildren() {
        return getChildren(null);
    }

    @SuppressWarnings({"unchecked"})
    public <T extends Enum<T>> Collection<XmlElement<T>> getChildren(final Class<T> childType) {
        final Collection<Element> children = childType != null ? element.getChildren(childType.getSimpleName()) : element.getChildren();
        final Collection<XmlElement<T>> result = new ArrayList<XmlElement<T>>(children.size());
        for (final Element child : children) {
            result.add(new JdomXmlElement<T>(childType, child));
        }
        return result;
    }
}
