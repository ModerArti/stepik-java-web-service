package com.github.moderarti.parsing.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ReadXMLFileSAX {
    public static Object readXML(String xmlFile) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            SaxHandler handler = new SaxHandler();

            saxParser.parse(xmlFile, handler);

            return handler.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
