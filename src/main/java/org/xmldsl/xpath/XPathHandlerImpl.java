package com.salt.alexa.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 19.02.2009 11:34:27
 */
public class XPathHandlerImpl implements XPathHandler {
    public String getXPathValue(final String xmlData, final String xpathExpression) {
        try {
            final Document doc = createDocument(xmlData);
            final XPathExpression expr = xpathExpression(xpathExpression);
            return evaluateAsString(doc, expr);
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression " + xpathExpression, e);
        }
    }

    private String evaluateAsString(final Document doc, final XPathExpression expr) throws XPathExpressionException {
        final Object result = expr.evaluate(doc, XPathConstants.STRING);
        if (result == null) return null;
        return result.toString();
    }

    private XPathExpression xpathExpression(final String xpathExpression) throws XPathExpressionException {
        final XPathFactory factory = XPathFactory.newInstance();
        final XPath xpath = factory.newXPath();
        return xpath.compile(xpathExpression);
    }

    private Document createDocument(final String xmlData) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        final DocumentBuilder builder = domFactory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlData)));
    }
}
