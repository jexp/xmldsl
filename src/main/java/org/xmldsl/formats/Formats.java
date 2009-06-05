package org.xmldsl.formats;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public interface Formats<T> {
    class ParseResult<T> {
        public final boolean parsed;
        public final T value;

        private ParseResult(final T value) {
            this.value = value;
            this.parsed = true;
        }

        private ParseResult() {
            this.parsed = false;
            this.value = null;
        }

        public static <T> ParseResult<T> unparsed() {
            return new ParseResult<T>();
        }
        public static <T> ParseResult<T> parsed(final T value) {
            return new ParseResult<T>(value);
        }
        
    }

    String format(T value);

    ParseResult<T> parseObject(String value);

    Formats<?> NULL_FORMATS = new Formats<Object>() {
        public String format(final Object value) {
            return null;
        }

        public ParseResult<Object> parseObject(final String value) {
            return ParseResult.unparsed();
        }
    };
}
