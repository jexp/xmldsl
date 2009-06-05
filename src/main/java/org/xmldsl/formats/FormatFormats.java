package org.xmldsl.formats;

import java.text.*;
import java.util.*;

public class FormatFormats<T> implements Formats<T> {
    private Collection<Format> formats;
    private final Class<T> type;

    @SuppressWarnings({"unchecked"})
    public FormatFormats(final String[] formatStrings, final Class type) {
        this.type = type;
        this.formats = createFormats(formatStrings);
    }

    private boolean isString() {
        return CharSequence.class.isAssignableFrom(type);
    }

    public String format(final T value) {
        final Collection<Format> formats = this.formats;
        if (formats.isEmpty()) {
            return null;
        }
        Exception lastException = null;
        for (final Format format : formats) {
            try {
                return format.format(value);
            } catch (Exception e) {
                lastException = e;
            }
        }
        throw new RuntimeException(String.format("Error formatting value %s using formats %s", value, formats), lastException);
    }

    @SuppressWarnings({"unchecked"})
    public ParseResult<T> parseObject(final String value) {
        final Collection<Format> formats = this.formats;
        if (formats.isEmpty()) {
            return ParseResult.unparsed();
        }
        Exception lastException = null;
        for (final Format format : formats) {
            try {
                return ParseResult.parsed((T) format.parseObject(value));
            } catch (ParseException e) {
                lastException = e;
            }
        }
        throw new RuntimeException(String.format("Error parsing value %s to type %s using formats %s", value, type, formats), lastException);
    }

    private Collection<Format> createFormats(String[] formatStrings) {
        if (formatStrings == null || formatStrings.length == 0) return Collections.emptyList();
        final Collection<Format> result = new ArrayList<Format>(formatStrings.length);
        for (final String format : formatStrings) {
            result.add(createFormat(type, format));
        }
        return result;
    }

    private Format createFormat(final Class<T> type, final String format) {
        if (isString()) return new SingleMessageFormat(format);
        if (Number.class.isAssignableFrom(type)) return new DecimalFormat(format);
        if (Date.class.isAssignableFrom(type)) return new SimpleDateFormat(format);
        throw new RuntimeException(String.format("Unknown format %s for type %s", format, type));
    }
}