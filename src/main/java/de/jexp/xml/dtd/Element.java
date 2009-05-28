package de.jexp.xml.dtd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mcr
 * @since 26.05.2009
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Element {
    enum Count {
        ZERO, ONE, ONE_OR_MORE, MANY;
    }
    public abstract Count count() default Count.MANY;
    Class<? extends Enum<?>> value();
}