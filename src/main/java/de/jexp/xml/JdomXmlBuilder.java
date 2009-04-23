package de.jexp.xml;

import org.jdom.Element;
import org.jdom.Text;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;

import java.util.LinkedHashMap;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class JdomXmlBuilder extends AbstractXmlBuilder {
    LinkedHashMap<Tag, Element> stack = new LinkedHashMap<Tag, Element>();

    @Override
    public Element getDelegate(final Tag<?> tag) {
        return stack.get(tag);
    }

    @Override
    public void addAttribute(final Tag<?> tag, final String name, final String value) {
        element(tag).setAttribute(name, value);
    }

    @Override
    public void addText(final Tag<?> tag, final String text) {
        element(tag).setContent(new Text(text));
    }

    private Element element(final Tag<?> tag) {
        final Element element = getDelegate(tag);
        if (element != null) return element;
        final Element newElement = new Element(tag.getName());
        stack.put(tag, newElement);
        return newElement;
    }

    @Override
    public String toXml(final Tag<?> tag) {
        final XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat());
        return xmlOutputter.outputString(getDelegate(tag));
    }

    @Override
    public void addChild(final Tag<?> parent, final Tag<?> child) {
        final Element parentElement = element(parent);
        final Element childElement = element(child);
        parentElement.addContent(childElement);
        stack.remove(child);
    }

}