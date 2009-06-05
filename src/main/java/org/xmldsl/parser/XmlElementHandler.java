package org.xmldsl.parser;

public interface XmlElementHandler<E extends Enum<E>> {
    boolean handle(XmlElement<E> element);
}