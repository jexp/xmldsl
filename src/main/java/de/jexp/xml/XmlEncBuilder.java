package de.jexp.xml;

import org.znerd.xmlenc.XMLOutputter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Stack;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class XmlEncBuilder extends AbstractXmlBuilder {
    private final XMLOutputter xml;

    public XmlEncBuilder(final Writer writer) {
        try {
            xml = new XMLOutputter(writer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public XMLOutputter getDelegate(final Tag<?> tag) {
        return xml;
    }

    @Override
    public void addAttribute(final Tag<?> tag, final String name, final String value) {
        if (value == null) return;
        try {
            xml.attribute(name, value);
        } catch (IOException e) {
            throw new RuntimeException("Error adding attribute " + name + "=" + value + " to " + tag, e);
        }
    }


    @Override
    public void addText(final Tag<?> tag, final String text) {
        if (text == null) return;
        try {
            xml.pcdata(text);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error adding text  to %s: %100s", tag, text), e);
        }
    }

    @Override
    public String toXml(final Tag<?> tag) {
        try {
            xml.endDocument();
            return xml.getWriter().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error ending Document for " + tag, e);
        }
    }

    final static Tag<?> block = null;
    final Stack<Tag> stack = new Stack<Tag>();

    @Override
    public <E extends Enum<E>> Tag<E> tag(final Class<E> tagEnum) {
        final Tag<E> tag = new Tag<E>(tagEnum, this);
        try {
            if (!stack.isEmpty() && stack.peek()!=block) endTag(stack.pop());

            xml.startTag(tag.getName());

            stack.push(tag);
            return tag;
        } catch (Exception e) {
            throw new RuntimeException("Error starting tag " + tag, e);
        }
    }

    @Override
    public void startTags(final Tag<?> tag) {
        stack.push(block);
    }

    @Override
    public void endTags(final Tag<?> tag) {
        while (!stack.isEmpty()) {
            final Tag<?> stackTag = stack.pop();
            if (stackTag != block)
                endTag(stackTag);
            else break;
        }
    }

    private void endTag(final Tag<?> tag) {
        try {
            xml.endTag();
        } catch (Exception e) {
            throw new RuntimeException("Error adding ending tag " + tag, e);
        }
    }
}