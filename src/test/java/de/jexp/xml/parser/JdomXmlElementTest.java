package de.jexp.xml.parser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import de.jexp.xml.PERSON;
import de.jexp.xml.Size;
import de.jexp.xml.Gender;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class JdomXmlElementTest {
    @Test
    public void testGetByDtd() throws ParseException {
        final XmlElement<PERSON> element = JdomXmlElement.Create.from(PERSON.class,
                "<PERSON SIZE=\"130\" AGE=\"30\" NAME=\"Micha\">" +
                        "<CHILD NAME=\"Kind Selma\" MOOD=\"funny\" GENDER=\"WEIBLICH\" BIRTHDAY=\"08/05/03\"/>" +
                        "</PERSON>");

        assertEquals(new Size(130), element.get(PERSON.SIZE));
        assertEquals(30, element.get(PERSON.AGE));
        assertEquals("Micha", element.get(PERSON.NAME));

        element.handleChildren(PERSON.CHILD.class, new XmlElementHandler<PERSON.CHILD>() {
            public boolean handle(final XmlElement<PERSON.CHILD> childXmlElement) {
                assertEquals("Selma", childXmlElement.get(PERSON.CHILD.NAME));
                final Date bday = parseDate("03.05.2008");
                assertEquals(bday, childXmlElement.get(PERSON.CHILD.BIRTHDAY));
                assertEquals(Gender.WEIBLICH, childXmlElement.get(PERSON.CHILD.GENDER));
                assertEquals(true, childXmlElement.get(PERSON.CHILD.MOOD));
                return false;
            }
        });
    }

    private static Date parseDate(final String dateString) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
