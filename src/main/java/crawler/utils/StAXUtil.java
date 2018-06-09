package crawler.utils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StAXUtil {
    public static XMLEventReader getEventReader(String filePath) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);


        XMLEventReader reader = null;
        reader = factory.createXMLEventReader(new FileInputStream(filePath), "UTF-8");
        return reader;
    }

    public static String extractAttr(StartElement mainE, String qName) {
        Attribute attr = mainE.getAttributeByName(new QName(qName));
        if (attr != null) {
            return attr.getValue();
        }
        return "";
    }

    public static String cleanString(String raw) {
        // clean entity reference in text
        String cleaned = raw.replaceAll("&[a-zA-Z]+=[A-Za-z]+","%29");
        // clean unclosed tag
        //img
        boolean isImgExist = true;
        Pattern pattern = Pattern.compile("<img src=\"[^\"]+\">");
        Matcher matcher;

        int pos = 0;
        while (isImgExist && pos < cleaned.length()) {
            matcher = pattern.matcher(cleaned);
            if (matcher.find()) {
                String tagString = matcher.group(0);
                pos = cleaned.indexOf(tagString) + tagString.length();
                if (pos < cleaned.length()) {
                    cleaned = cleaned.substring(0, pos) + tagString + "</img>" + cleaned.substring(pos + 1, cleaned.length());
                } else  {
                    cleaned = cleaned + "</img>";
                    isImgExist = false;
                }
            } else {
                isImgExist = false;
            }
        }
        return cleaned.trim();
    }
}
