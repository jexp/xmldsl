package de.jexp.xml;

import de.jexp.xml.dtd.Constraint;
import de.jexp.xml.dtd.Mapping;
import de.jexp.xml.dtd.Elements;
import de.jexp.xml.dtd.Element;

import java.util.Date;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
@Elements(@Element(value = PERSON.CHILD.class,count = Element.Count.MANY))
public enum PERSON {
    NAME,
    @Constraint(type = Integer.class, min = 0)
    AGE,
    @Constraint(type = Size.class)
    SIZE;

    public enum CHILD {
        @Constraint(type = String.class, format = {"Kind {0}"})
        NAME,
        @Constraint(type = Date.class, format = {"dd.MM.yyyy", "yy/MM/dd", "dd-MM-yyyy"})
        BIRTHDAY,
        @Constraint(type = Gender.class)
        GENDER,
        @Constraint(type = Boolean.class, mapping = {@Mapping(fromSpecial = Mapping.Special.TRUE, to = "funny"),@Mapping(fromSpecial = Mapping.Special.FALSE, to = "sad"),@Mapping(fromSpecial = Mapping.Special.NULL, to = "boring")})
        MOOD
    }
}
