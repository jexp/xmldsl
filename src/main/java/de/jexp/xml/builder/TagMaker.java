package de.jexp.xml.builder;

/**
 * @author Michael Hunger
 * @since 27.05.2009
 */
public interface TagMaker<F,T extends Enum<T>> {

    Tag<T> makeTag(F value);
}
