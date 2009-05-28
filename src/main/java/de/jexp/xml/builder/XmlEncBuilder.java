package de.jexp.xml.builder;

import org.znerd.xmlenc.XMLOutputter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class XmlEncBuilder extends AbstractXmlBuilder {
    private final XMLOutputter xml;

    public XmlEncBuilder() {
        this(new StringWriter());
    }

    public XmlEncBuilder(final Writer writer) {
        try {
            xml = new XMLOutputter(writer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public XMLOutputter getDelegate(final Tag<?> tag) {
        return xml;
    }

    public void addAttribute(final Tag<?> tag, final String name, final String value) {
        if (value == null) return;
        try {
            xml.attribute(name, value);
        } catch (IOException e) {
            throw new RuntimeException("Error adding attribute " + name + "=" + value + " to " + tag, e);
        }
    }

    public void addText(final Tag<?> tag, final String text) {
        if (text == null) return;
        try {
            xml.pcdata(text);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error adding text  to %s: %100s", tag, text), e);
        }
    }

    public String toXml(final Tag<?> tag) {
        try {
            xml.endDocument();
            return xml.getWriter().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error ending Document for " + tag, e);
        }
    }

    public void addChild(final Tag<?> parent, final Tag<?> child) {
        childExistInParent(parent, child);
    }

    public void endTag(final Tag<?> tag) {
        try {
            xml.endTag();
        } catch (IOException e) {
            throw new RuntimeException("Error adding ending tag " + tag, e);
        }
    }

    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum) {
        final Tag<E> tag = new Tag<E>(tagEnum, this);
        try {
            xml.startTag(tag.getName());
            return tag;
        } catch (IOException e) {
            throw new RuntimeException("Error starting tag " + tag, e);
        }
    }
}