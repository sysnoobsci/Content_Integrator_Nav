package com.systemware.contentintegrator.app;

/**
 * Created by john.williams on 5/27/2014.
 */
import android.util.Log;
import android.util.Xml;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * Created by adrian.meraz on 5/16/2014.
 */
public class XmlParser {
    private static Boolean is_xml = false;
    String xmlstring = "";
    String searchfor = "";

    public XmlParser(String searchfor){
        setSearchfor(searchfor);
    }

    public String getSearchfor() {
        return searchfor;
    }

    public void setSearchfor(String searchfor) {
        this.searchfor = searchfor;
    }

    public String getXmlstring() {
        return xmlstring;
    }

    public void setXmlstring(String xmlstring) {
        this.xmlstring = xmlstring;
    }

    static StringBuilder total = new StringBuilder();

    public static Boolean getIs_xml() {
        return is_xml;
    }

    public static void setIs_xml(Boolean is_xml) {
        XmlParser.is_xml = is_xml;
    }

    public static String parseXMLfunc(String xmlstring)
            throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( new StringReader ( xmlstring ) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                //total.append("Start document\n");
            } else if(eventType == XmlPullParser.START_TAG) {
                //total.append("Start tag "+xpp.getName()+"\n");
            } else if(eventType == XmlPullParser.END_TAG) {
                //total.append("End tag "+xpp.getName()+"\n");
            } else if(eventType == XmlPullParser.TEXT) {
                total.append(xpp.getText());
                //total.append("Text "+xpp.getText()+"\n");
            }
            eventType = xpp.next();
        }
        //total.append("End document\n");
        Log.d("Variable", "XML Contents: " + total.toString());
        return total.toString();//return parsed contents of XML
    }
    protected Boolean isXMLformat(String xmlstring){
        String str2 = "<?xml version=";
        setIs_xml(xmlstring.toLowerCase().contains(str2.toLowerCase()));
        return getIs_xml();
    }

}