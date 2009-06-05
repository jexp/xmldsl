package org.xmldsl.schema.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mcr
 * @since 26.05.2009
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Mapping {
    enum Special {
        TRUE, FALSE, NULL, UNSET
    }
    public String from() default "";

    public double fromNumber() default Double.NaN;

    public Special fromSpecial() default Special.UNSET;

    public String to();
}