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
@Target(ElementType.FIELD)
public @interface Constraint {
    /**
     * any of the basic types (String, Number types, Boolean, Enum(s)) or a custom type that supports either a String constructor or a static valueOf() factory method
     */
    Class type() default String.class;

    double min() default Double.NEGATIVE_INFINITY;

    double max() default Double.POSITIVE_INFINITY;

    /**
     * either SimpleDateFormat, DecimalFormat or MessageFormat (with one placeholder)
     * they are tried in ascending order if the previous failed
     */
    String[] format() default {};

    Mapping[] mapping() default {};
}
