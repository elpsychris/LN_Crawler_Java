package crawler.utils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StAXUtils {
    public static XMLEventReader getEventReader(InputStream is) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);


        XMLEventReader reader = null;
        reader = factory.createXMLEventReader(is, "UTF-8");
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
        String cleaned = raw.replaceAll("&", "%29;");
        // clean br tag
        cleaned = cleaned.replaceAll("<br>", "");
        cleaned = cleaned.replaceAll("<br/>", "");
        cleaned = cleaned.replaceAll("</br>", "");

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
                } else {
                    cleaned = cleaned + "</img>";
                    isImgExist = false;
                }
            } else {
                isImgExist = false;
            }
        }
        return cleaned.trim();
    }

    public static String cleanDocument(String rawDoc) {
        String cleaned = rawDoc;
        Pattern pattern = Pattern.compile("<[^!>]+<");
        Matcher matcher = pattern.matcher(cleaned);

        while (matcher.find()) {
            String matchedStr = matcher.group(0);
            int pos = cleaned.indexOf(matchedStr) + matchedStr.length() - 1;
            cleaned = cleaned.substring(0, pos - 1) + ">" + cleaned.substring(pos - 1);
        }


        return cleaned.trim();
    }
}
