package crawler.utils;


import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class MyURIResolver implements URIResolver {
    private final static String XML_DECLARATION = "?xml version='1.0' encoding='UTF-8' standalone='no' ?>";

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null && href.indexOf("https://ln.hako.re/danh-sach?page=1") == 0) {
            try {
                InputStream httpResult = ComUtils.getHttp(href);
                StreamSource streamSource = preProcessInputStream(httpResult);
                return streamSource;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private StreamSource preProcessInputStream(InputStream httpResult) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(httpResult, "UTF-8");

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        stringBuffer.append(XML_DECLARATION + "\n");
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("<html") && line.contains("xmlns=\"http://www.w3.org/1999/xhtml\"")) {
                line = line.replace("<html", "<html xmlns=\"http://www.w3.org/1999/xhtml\"");
            }
            if (line.contains("src") || line.contains("href")) {
                line = line.replace("&", "&amp;");
            }

            // (R) char and ... and non-breaking space char replacement
            line = line
                    .replace("&reg;", "&#174;")
                    .replace("&hellip;", "")
                    .replace("&nbsp;", "");

            stringBuffer.append(line + "\n");
        }

        InputStream htmlResult = new ByteArrayInputStream(stringBuffer.toString().getBytes());
        return new StreamSource(htmlResult);
    }

}
