package de.jexp.xml.dtd;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import de.jexp.xml.PERSON;
import de.jexp.xml.Size;
import de.jexp.xml.Gender;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class XmlFormatTest {
    private XmlFormat<PERSON> personFormat;
    private static final int AGE_23 = 23;
    private static final Size SIZE_130 = new Size(130);
    private XmlFormat<PERSON.CHILD> childFormat;
    private static final String B_DAY_STRING = "75/01/13";
    private static final String B_DAY_STRING2 = "13.01.1975";
    private static final String B_DAY_STRING3 = "13-01-1975";
    private static final Date B_DAY = parseDate(B_DAY_STRING2);

    private static Date parseDate(final String dateString)  {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParsePerson() {
        assertEquals(AGE_23,personFormat.parse(PERSON.AGE,"23"));
        assertEquals(SIZE_130,personFormat.parse(PERSON.SIZE,"130"));
    }
    @Test
    public void testParseChild() {
        assertEquals(B_DAY,childFormat.parse(PERSON.CHILD.BIRTHDAY, B_DAY_STRING));
        assertEquals(B_DAY,childFormat.parse(PERSON.CHILD.BIRTHDAY, B_DAY_STRING2));
        assertEquals(B_DAY,childFormat.parse(PERSON.CHILD.BIRTHDAY, B_DAY_STRING3));
        assertEquals(Gender.MAENNLICH,childFormat.parse(PERSON.CHILD.GENDER, "MAENNLICH"));
        assertEquals(Gender.WEIBLICH,childFormat.parse(PERSON.CHILD.GENDER, "WEIBLICH"));
        assertEquals(null,childFormat.parse(PERSON.CHILD.GENDER, null));

        assertEquals(Boolean.TRUE,childFormat.parse(PERSON.CHILD.MOOD, "funny"));
        assertEquals(Boolean.FALSE,childFormat.parse(PERSON.CHILD.MOOD, "sad"));
        assertEquals(null,childFormat.parse(PERSON.CHILD.MOOD, "boring"));

        assertEquals("Rana",childFormat.parse(PERSON.CHILD.NAME, "Kind Rana"));
    }
    @Test
    public void testFormatChild() {
        assertEquals(B_DAY_STRING2,childFormat.format(PERSON.CHILD.BIRTHDAY, B_DAY));
        assertEquals("MAENNLICH",childFormat.format(PERSON.CHILD.GENDER, Gender.MAENNLICH));
        assertEquals("WEIBLICH",childFormat.format(PERSON.CHILD.GENDER, Gender.WEIBLICH));
        assertEquals(null,childFormat.format(PERSON.CHILD.GENDER, null));

        assertEquals("funny",childFormat.format(PERSON.CHILD.MOOD, true));
        assertEquals("sad",childFormat.format(PERSON.CHILD.MOOD, false));
        assertEquals("boring",childFormat.format(PERSON.CHILD.MOOD, null));

        assertEquals("Kind Selma",childFormat.format(PERSON.CHILD.NAME, "Selma"));
    }

    @Test
    public void testFormatPerson() {
        assertEquals("23",personFormat.format(PERSON.AGE,"23"));
        assertEquals("23",personFormat.format(PERSON.AGE,AGE_23));
        assertEquals("130",personFormat.format(PERSON.SIZE,"130"));
        assertEquals("130",personFormat.format(PERSON.SIZE,SIZE_130));
    }

    @Before
    public void setUp() throws Exception {
        personFormat = new XmlFormat<PERSON>(PERSON.class);
        childFormat = new XmlFormat<PERSON.CHILD>(PERSON.CHILD.class);
    }
}
