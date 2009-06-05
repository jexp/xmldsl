package org.xmldsl.builder;

import java.util.Map;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public interface XmlBuilder {

    <E extends Enum<E>> Tag<E> tag(Class<E> tagEnum);

    <E extends Enum<E>> Tag<E> tag(Class<E> tagEnum, E name, Object value);

    <E extends Enum<E>> Tag<E> tag(Class<E> tagEnum, E name, Object value, E name2, Object value2);

    <E extends Enum<E>> Tag<E> tag(Class<E> tagEnum, E name, Object value, E name2, Object value2, E name3, Object value3);

    <E extends Enum<E>> Tag<E> tag(Class<E> tagEnum, Map<E, ?> attributes);

    void addChild(Tag<?> eTag, Tag<?> child);

    String toXml(Tag<?> tag);

    Object getDelegate(Tag<?> tag);

    void addText(Tag<?> tag, String text);

    void addAttribute(Tag<?> tag, String name, String value);

    void endTag(Tag<?> tag);

    void endTags(Tag<?> tag);

    void startTags(Tag<?> tag);

    XmlBuilder checkStructure();
}