package org.xmldsl.schema.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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