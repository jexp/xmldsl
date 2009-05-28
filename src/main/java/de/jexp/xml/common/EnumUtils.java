package de.jexp.xml.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class EnumUtils {
    public static <A extends Annotation> A getEnumAnnotation(final Enum attribute, final Class<A> annotation) {
        try {
        final Class declaringClass = attribute.getDeclaringClass();
        final Field enumField = declaringClass.getDeclaredField(attribute.name());
        return enumField.getAnnotation(annotation);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("No Field %s in %s",attribute,attribute.getDeclaringClass()),e);
        }
    }
}
