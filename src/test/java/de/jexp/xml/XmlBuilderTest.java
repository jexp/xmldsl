package de.jexp.xml;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    private static final int COUNT = 10000;
    private static final String COMPLEX_CONTENT = "<CHILD>" +
            "<PERSON/><CHILD/>" +
            "<PERSON>" +
            "<CHILD/><CHILD/><CHILD/><CHILD/>" +
            "</PERSON>" +
            "</CHILD>" +
            "<CHILD/>";
    private static final String COMPLEX_XML = "<PERSON>" + COMPLEX_CONTENT + "</PERSON>";

    @Test
    public void testCreateXml() throws ParseException {
        checkBuilder(new JdomXmlBuilder());
    }

    @Test
    public void testCreateXmlEnc() throws ParseException {
        checkBuilder(new XmlEncBuilder(new StringWriter()));
    }

    private void checkBuilder(final XmlBuilder xml) throws ParseException {

        final Date ranaBirthday = new SimpleDateFormat("dd.MM.yyyy").parse("15.09.2005");
        final Date selmaBirthday = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse("03.05.2008 12:00");

        final Tag<PERSON> person = xml.tag(PERSON.class,NAME, "Michael",AGE, 34).attr(SIZE, new DecimalFormat("#000.00"), 1.5)
                .tags().add(                                                        
                        xml.tag(CHILD.class,CHILD.NAME, "Rana").attr(CHILD.BIRTHDAY, "dd.MM.yyyy", ranaBirthday),
                        xml.tag(CHILD.class).attr(CHILD.NAME, "Selma").attr(CHILD.BIRTHDAY, selmaBirthday).text("Baby")
                );

        final String expected =
                "<PERSON NAME=\"Michael\" AGE=\"34\" SIZE=\"001,50\">" +
                        "<CHILD NAME=\"Rana\" BIRTHDAY=\"15.09.2005\"/>" +
                        "<CHILD NAME=\"Selma\" BIRTHDAY=\"03.05.2008 12:00:00\">" +
                        "Baby" +
                        "</CHILD>" +
                        "</PERSON>";
        assertEqualXml(expected, person);
    }

    private void assertEqualXml(String expected, Tag<PERSON> person) {
        assertEquals(expected, person.toXml().replaceAll(" />","/>"));
    }

    @Test
    public void testName() {
        assertEquals("PERSON", new JdomXmlBuilder().tag(PERSON.class).getName());
    }

    @Test
    public void testComplexStructureXmlEnc() {
        checkComplexStructure(new XmlEncBuilder(new StringWriter()));
    }
    @Test
    public void testComplexStructureJdcom() {
        checkComplexStructure(new JdomXmlBuilder());
    }

    private void checkComplexStructure(XmlBuilder xml) {
        final Tag<PERSON> person = xml.tag(PERSON.class).tags().add(
                xml.tag(CHILD.class).tags().add(
                        xml.tag(PERSON.class), xml.tag(CHILD.class),
                        xml.tag(PERSON.class).tags().add(
                                xml.tag(CHILD.class), xml.tag(CHILD.class)
                        ).tags().add(
                                xml.tag(CHILD.class), xml.tag(CHILD.class)
                        )),
                xml.tag(CHILD.class)
        );
        assertEqualXml(COMPLEX_XML, person);
    }
    @Test(timeout = 750)
    public void testPerformance() {
        final XmlBuilder xml = new XmlEncBuilder(new StringWriter());
        final Tag<PERSON> person = xml.tag(PERSON.class);
        for (int i=0;i<COUNT;i++) {
        person.tags().add(
                xml.tag(CHILD.class).tags().add(
                        xml.tag(PERSON.class), xml.tag(CHILD.class),
                        xml.tag(PERSON.class).tags().add(
                                xml.tag(CHILD.class), xml.tag(CHILD.class)
                        ).tags().add(
                                xml.tag(CHILD.class), xml.tag(CHILD.class)
                        )),
                xml.tag(CHILD.class)
        );
        }
        final String result = person.toXml();
        System.out.println("result.length() = " + result.length());
        assertTrue("generated all "+result.length(),result.length() > COUNT*COMPLEX_CONTENT.length());
        assertTrue("contains part",result.contains(COMPLEX_CONTENT));
    }
}