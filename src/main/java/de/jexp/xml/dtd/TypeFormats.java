package de.jexp.xml.dtd;

import static de.jexp.xml.dtd.Formats.ParseResult.parsed;
import static de.jexp.xml.dtd.Formats.ParseResult.unparsed;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
@SuppressWarnings({"unchecked"})
public class TypeFormats<T> implements Formats<T> {
    private Class<T> type;

    public TypeFormats(final Class<T> type) {
        this.type = type;
    }

    public String format(final T value) {
        return value == null ? null : value.toString();
    }

    public ParseResult<T> parseObject(final String value) {
        if (isString()) return parsed((T)value);
        final ParseResult<T> result = parseWithValueOf(value);
        if (result.parsed) return result;
        return parseWithConstructor(value);
    }

    private ParseResult<T> parseWithConstructor(final String value) {
        try {
            final Constructor constructor = type.getDeclaredConstructor(String.class);
            return parsed((T) constructor.newInstance(value));
        } catch (Exception e) {
            return unparsed();
        }
    }

    private ParseResult<T> parseWithValueOf(final String value) {
        try {
            final Method valueOf = type.getDeclaredMethod("valueOf", String.class);
            if (!Modifier.isStatic(valueOf.getModifiers())) return null;
            return parsed((T) valueOf.invoke(null, value));
        } catch (Exception e) {
            return unparsed();
        }
    }

    private boolean isString() {
        return CharSequence.class.isAssignableFrom(type);
    }


    public static <T> Formats<T> from(final Class<T> type) {
        return new TypeFormats<T>(type);
    }
}
