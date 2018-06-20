package crawler.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;
import java.io.StringReader;

public class JAXBUtils {
    public static <T> T xmlToObject(DOMResult xml, Class<T>... classes) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classes);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T object = (T) unmarshaller.unmarshal(xml.getNode());
        return object;
    }


}
