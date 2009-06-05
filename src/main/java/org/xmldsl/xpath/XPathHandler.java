package com.salt.alexa.xml;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 19.02.2009 11:34:19
 */
public interface XPathHandler {
    String getXPathValue(String xmlData, String xpathExpression);
}
