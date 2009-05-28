package de.jexp.xml.dtd;

import java.text.Format;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.MessageFormat;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class SingleMessageFormat extends Format {
    private final MessageFormat format;

    public SingleMessageFormat(final String format) {
        this.format = new MessageFormat(format);
    }

    public StringBuffer format(final Object o, final StringBuffer stringBuffer, final FieldPosition fieldPosition) {
        return format.format(new Object[]{o},stringBuffer,fieldPosition);
    }

    public Object parseObject(final String s, final ParsePosition parsePosition) {
        final Object[] result = (Object[]) format.parseObject(s, parsePosition);
        return result[0];
    }
}
