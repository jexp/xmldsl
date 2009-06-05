package org.xmldsl.common;

import java.util.EnumMap;

/**
 * @author mcr
 * @since 24.04.2009
 */
public class Props<E extends Enum<E>> {
    final EnumMap<E, String> attributes;

    public Props(final Class<E> type, final EnumMap<E, String> values) {
        attributes = new EnumMap<E, String>(type);
        add(values);
    }

    public Props(final Class<E> type) {
        attributes = new EnumMap<E, String>(type);
    }

    public void add(final EnumMap<E, String> values) {
        if (values != null)
            attributes.putAll(values);
    }
    public void add(final E name, final String value) {
        attributes.put(name, value);
    }

    public EnumMap<E, String> getProps() {
        return attributes;
    }
}
