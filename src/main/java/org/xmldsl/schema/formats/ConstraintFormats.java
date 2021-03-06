package org.xmldsl.schema.formats;

import static org.xmldsl.formats.Formats.ParseResult.unparsed;
import org.xmldsl.schema.annotation.Constraint;
import org.xmldsl.formats.Formats;
import org.xmldsl.formats.FormatFormats;
import org.xmldsl.formats.TypeFormats;

import java.util.Collection;
import java.util.Arrays;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
@SuppressWarnings({"unchecked"})
public class ConstraintFormats<T> implements Formats<T> {
    private final Collection<Formats<T>> formats;

    public ConstraintFormats(final Constraint constraint) {
        if (constraint == null) throw new IllegalArgumentException("Constraint must not be null");

        final Class type = constraint.type();
        this.formats = Arrays.<Formats<T>>asList(new FormatFormats<T>(constraint.format(), type), new MappingFormats<T>(type, constraint.mapping()), new TypeFormats(type));
    }

    public ParseResult<T> parseObject(final String value) {
        for (final Formats<T> format : formats) {
            final ParseResult<T> result = format.parseObject(value);
            if (result.parsed) return result;
        }
        return unparsed();
    }

    public String format(final T value) {
        for (final Formats<T> format : formats) {
            final String result = format.format(value);
            if (result != null) return result;
        }
        return null;
    }
}