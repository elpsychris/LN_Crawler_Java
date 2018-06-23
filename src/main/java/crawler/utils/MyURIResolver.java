package crawler.utils;


import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class MyURIResolver implements URIResolver {
    private static volatile boolean isStop = false;
    private static volatile boolean isPause = false;
    private static InputStream empty = null;


    @Override
    public Source resolve(String href, String base) throws TransformerException {
        Logger.getLogger().info("Resolving content from: " + href);


        StringBuffer emptyHtml = new StringBuffer();
        emptyHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"></html>");
        empty = new ByteArrayInputStream(emptyHtml.toString().getBytes());
        while (isPause && !isStop) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Logger.getLogger().log(Logger.LOG_LEVEL.ERROR, "Crawler cannot be paused", e);
            }
        }
        ;
        if (href != null && !isStop) {
            try {
                InputStream httpResult = ComUtils.getHttp(href);
                StreamSource streamSource = preProcessInputStream(httpResult);
                return streamSource;
            } catch (InterruptedException e) {
                Logger.getLogger().log(Logger.LOG_LEVEL.ERROR, "Crawler cannot be paused", e);
            } catch (IOException e) {
                Logger.getLogger().log(Logger.LOG_LEVEL.ERROR, "Error in resolving content from URL", e);
            }
        }

        return new StreamSource(empty);
    }

    public void pause() {
        isPause = true;
        Logger.getLogger().info("Pause signal for Crawler fired");
    }

    public void stop() {
        isStop = true;
        Logger.getLogger().info("Stop signal for Crawler fired");

    }

    public void cont() {
        isPause = false;
        Logger.getLogger().info("Continue signal for Crawler fired");
    }

    private StreamSource preProcessInputStream(InputStream httpResult) throws IOException {
        StringBuffer stringBuffer = ComUtils.cleanHTML(httpResult);
        InputStream htmlResult = new ByteArrayInputStream(stringBuffer.toString().getBytes());
        return new StreamSource(htmlResult);
    }


}
