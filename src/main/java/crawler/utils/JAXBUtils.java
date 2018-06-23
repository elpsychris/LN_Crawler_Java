package crawler.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBUtils {
    private final static SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    public static <T> T xmlToObject(DOMResult xml, Class<T>... classes) throws JAXBException {
        return xmlToObject(xml, null, classes);
    }

    public static <T> T xmlToObject(DOMResult xml, Schema schema, Class<T>... classes) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classes);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        if (schema != null) {
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
        }
        T object = null;
        try {
            object = (T) unmarshaller.unmarshal(xml.getNode());
        }catch (UnmarshalException ex) {
            ex.printStackTrace();
        }
        return object;
    }

    public static Schema getSchema(String... schemaUrls) throws SAXException {
        Source[] sources = new Source[schemaUrls.length];
        for (int i = 0; i < schemaUrls.length; i++) {
            sources[i] = new StreamSource(new File(schemaUrls[i]));
        }
        return schemaFactory.newSchema(sources);
    }

}

class MyValidationEventHandler implements ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent event) {
        System.out.println("\nEVENT");
        System.out.println("SEVERITY: " + event.getSeverity());
        System.out.println("MESSAGE: " + event.getMessage());
        System.out.println("LINKED EXCEPTION: " + event.getLinkedException());
        System.out.println("LOCATOR");
        System.out.println("\t\tLINE NUMBER: " + event.getLocator().getLineNumber());
        System.out.println("\t\tCOL NUMBER: " + event.getLocator().getColumnNumber());
        System.out.println("\tOFFSET: " + event.getLocator().getOffset());
        System.out.println("OBJECT: " + event.getLocator().getObject());
        System.out.println("\t\tNODE: " + event.getLocator().getNode());
        System.out.println("\t\tURL: " + event.getLocator().getURL());
        return true;
    }
}
