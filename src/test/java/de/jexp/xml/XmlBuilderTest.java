package de.jexp.xml;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static de.jexp.xml.PERSON.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.io.StringWriter;

/**
 * @author Michael Hunger
 * @since 22.04.2009
 */
public class XmlBuilderTest {

    @Test
    public void testCreateXml() throws ParseException {
        checkBuilder(new JdomXmlBuilder());
    }

    @Test
    public void testCreateXmlEnc() throws ParseException {
        checkBuilder(new XmlEncBuilder(new StringWriter()));
    }

    private void checkBuilder(final XmlBuilder xml) throws ParseException {
        final String expected =
                "<PERSON NAME=\"Michael\" AGE=\"34\" SIZE=\"001,50\">" +
                        "<CHILD NAME=\"Rana\" GEBURT=\"15.09.2005\"/>" +
                        "<CHILD NAME=\"Selma\" GEBURT=\"03.05.2008 12:00:00\">" +
                        "Baby" +
                        "</CHILD>" +
                        "</PERSON>";

        final Date ranaGeburt = new SimpleDateFormat("dd.MM.yyyy").parse("15.09.2005");
        final Date selmaGeburt = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("03.05.2008 12:00");

        final Tag<PERSON> person = xml.tag(PERSON.class,NAME, "Michael",AGE, 34).attr(SIZE, new DecimalFormat("#000.00"), 1.5)
                .tags(                                                        
                        xml.tag(CHILD.class,CHILD.NAME, "Rana").attr(CHILD.GEBURT, "dd.MM.yyyy", ranaGeburt).end(),
                        xml.tag(CHILD.class).attr(CHILD.NAME, "Selma").attr(CHILD.GEBURT, selmaGeburt).text("Baby").end()
                );
        assertEquals(expected, person.toXml().replaceAll(" />","/>"));
    }

    @Test
    public void testName() {
        assertEquals("PERSON", new JdomXmlBuilder().tag(PERSON.class).getName());
    }
}