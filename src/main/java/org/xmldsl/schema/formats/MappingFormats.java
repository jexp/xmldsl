package org.xmldsl.schema.formats;

import static org.xmldsl.formats.Formats.ParseResult.parsed;
import static org.xmldsl.formats.Formats.ParseResult.unparsed;
import org.xmldsl.schema.annotation.Mapping;
import org.xmldsl.formats.Formats;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class MappingFormats<T> implements Formats<T> {
    Map<Object, String> mappings = new HashMap<Object, String>();
    private final Class<T> type;

    public MappingFormats(final Class<T> type, final Mapping[] mappings) {
        this.type = type;
        this.mappings = read(mappings);
    }

    private Map<Object, String> read(final Mapping[] mappings) {
        if (mappings == null || mappings.length == 0) return Collections.emptyMap();

        final Map<Object, String> result = new HashMap<Object, String>();
        for (final Mapping mapping : mappings) {
            result.put(from(mapping), mapping.to());
        }

        if (result.isEmpty()) return Collections.emptyMap();
        return result;
    }

    @SuppressWarnings({"FeatureEnvy"})
    private Object from(final Mapping mapping) {
        if (mapping.fromSpecial() == Mapping.Special.NULL) return null;
        if (CharSequence.class.isAssignableFrom(type)) return mapping.from();
        if (Number.class.isAssignableFrom(type)) return mapping.fromNumber();
        if (Boolean.class.isAssignableFrom(type)) {
            switch (mapping.fromSpecial()) {
                case TRUE:
                    return Boolean.TRUE;
                case FALSE:
                    return Boolean.FALSE;
            }
        }
        throw new RuntimeException(String.format("Mapping for type %s not set correctly at least one fromXxx must be set %s", type, mapping));
    }

    public String format(final T value) {
        return mappings.get(value);
    }

    @SuppressWarnings({"unchecked"})
    public ParseResult<T> parseObject(final String value) {
        for (final Map.Entry<Object, String> entry : mappings.entrySet()) {
            if (entry.getValue().equals(value)) return parsed((T) entry.getKey());
        }
        return unparsed();
    }
}
