package org.xmldsl.parser;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xmldsl.schema.XmlFormat;

/**
 * @author mcr
 * @since 27.04.2009
 */
public abstract class AbstractXmlElement<E extends Enum<E>> implements XmlElement<E> {
    private final Class<E> type;
    private XmlFormat<E> xmlFormat;

    public AbstractXmlElement(final Class<E> type) {
        this.type = type;
        this.xmlFormat = new XmlFormat<E>(type);
    }

    public Class<E> getType() {
        return type;
    }

    public <T> T get(final E attribute) {
        return xmlFormat.<T>parse(attribute, getValue(attribute));
    }

    public String getValue(final E attribute, final String defaultValue) {
       final String value = getValue(attribute);
        if(value == null)
            return defaultValue;
        return value;
    }

    public Integer getInteger(final E attribute) {
        final String value = getValue(attribute);
        if (isEmpty(value)) return null;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            throw failure(attribute);
        }
    }

    private RuntimeException failure(final E attribute) {
        return new RuntimeException("Error parsing xmldsl element " + this + " Attribute " + attribute);
    }

    private boolean isEmpty(final String value) {
        return value == null || value.trim().length() == 0;
    }

    public Date getDate(final E attribute, final String... formate) {
        final String value = getValue(attribute);
        if (isEmpty(value)) return null;
        for (final String format : formate) {
            try {
                return getDateFormat(format).parse(value);
            } catch (ParseException e) {
                // later
            }
        }
        throw failure(attribute);
    }

    ThreadLocal<Map<String, DateFormat>> dateFormats = new ThreadLocal<Map<String, DateFormat>>() {
        @Override
        protected Map<String, DateFormat> initialValue() {
            return new HashMap<String, DateFormat>();
        }
    };

    protected DateFormat getDateFormat(final String format) {
        final Map<String, DateFormat> cache = dateFormats.get();
        final DateFormat dateFormat = cache.get(format);
        if (dateFormat != null) return dateFormat;
        final DateFormat newDateFormat = new SimpleDateFormat(format);
        cache.put(format, newDateFormat);
        return newDateFormat;
    }


    public BigDecimal getBigDecimal(final E attribute) {
        final String value = getValue(attribute);
        if (isEmpty(value)) return null;
        try {
            return new BigDecimal(value.replace(',', '.'));
        } catch (NumberFormatException nfe) {
            throw failure(attribute);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " <" + getName() + ">" + getAttributeNames();
    }

    public <C extends Enum<C>> void handleChildren(final Class<C> childElementType, final XmlElementHandler<C> handler) {
        handleChildren(childElementType, handler, 0);
    }

    public <C extends Enum<C>> void handleChildren(final Class<C> childElementType, final XmlElementHandler<C> handler, final int depth) {
        final Collection<XmlElement<C>> elementList = getChildren(childElementType, depth);
        for (final XmlElement<C> child : elementList) {
            if (!handler.handle(child)) return;
        }
    }

    protected E enumByName(final String name) {
        for (final E enumAttribute : getType().getEnumConstants()) {
            if (enumAttribute.name().equalsIgnoreCase(name)) return enumAttribute;
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public Collection<E> getAttributeNames() {
        return EnumSet.copyOf(getAttributes().keySet());
    }

    private enum UNKNOWN {
    }

    /**
     * @param requiredElementType Typ der KinderElemente
     * @param depth               0 bedeutet direkte Kinderelemente werden geholt, 1 bedeutet Kinder der n�chsten Ebene werden geholt, ...
     * @param <C>                 Typ der XmlElemente
     * @return Collection von XmlElemente des spezifizierten Typs der KinderElemente
     */
    public <C extends Enum<C>> Collection<XmlElement<C>> getChildren(final Class<C> requiredElementType, final int depth) {
        if (depth > 0) {
            final List<XmlElement<C>> foundElements = new LinkedList<XmlElement<C>>();
            for (final XmlElement<UNKNOWN> child : this.<UNKNOWN>getChildren()) {
                final Collection<XmlElement<C>> foundChildren = child.getChildren(requiredElementType, depth - 1);
                foundElements.addAll(foundChildren);
            }
            return foundElements;
        } else {
            return getChildren(requiredElementType);
        }
    }
}
