package com.github.moderarti.parsing.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import com.github.moderarti.reflection.ReflectionHelper;

import java.util.logging.Logger;

public class SaxHandler extends DefaultHandler {

    private static final Logger log = Logger.getGlobal();
    private static final String CLASSNAME = "class";
    private String element = null;
    private Object object = null;

    @Override
    public void startDocument() {
        //System.out.println("Start document.");
    }

    @Override
    public void endDocument() {
        //System.out.println("End document.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        //log.info("uri = " + uri + "; localName = " + localName + "; qName = " + qName
                //+ "; attributes = " + attributes.toString());
        if (!qName.equals(CLASSNAME)) {
            element = qName;
        } else {
            String className = attributes.getValue(0);
            //System.out.println("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        element = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (element != null) {
            String value = new String(ch, start, length);
            //System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    public Object getObject() {
        return object;
    }
}
