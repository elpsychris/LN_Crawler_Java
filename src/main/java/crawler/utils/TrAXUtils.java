package crawler.utils;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class TrAXUtils {
    private static MyURIResolver uriResolver = new MyURIResolver();
    private static TransformerFactory factory = TransformerFactory.newInstance();


    public static DOMResult transform(StreamSource from, StreamSource xsl) throws TransformerException, IOException, InterruptedException {
        factory.setURIResolver(uriResolver);
        DOMResult rsDOM = new DOMResult();
//                StreamResult rsDOM = new StreamResult(new FileWriter(new File("src/main/java/crawl_temp/result.xml")));
        Transformer transformer = factory.newTransformer(xsl);
        transformer.transform(from, rsDOM);
        return rsDOM;
    }

    public static void stop() {
        uriResolver.stop();
    }


}
