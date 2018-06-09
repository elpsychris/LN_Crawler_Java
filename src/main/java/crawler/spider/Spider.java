package crawler.spider;


import crawler.utils.StAXUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

abstract class Spider {
    final String DEFAULT_PARENT_PATH = "src/main/java/crawl_temp";

    interface ParserHandler {
        public void onParse(boolean silent) throws InterruptedException;

        public void onError(Exception e);
    }


    public void setStartUrls(List<String> startUrls) {
        try {
            execute(startUrls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void setStartUrls(List<String> startUrls, ParserHandler handler) {
        execute(startUrls, handler);
    }

    public void setStartUrls(String startUrl) {
        List<String> startUrls = new ArrayList<String>();
        startUrls.add(startUrl);
        setStartUrls(startUrls);
    }

    //
    private void getContent(String filePath, String uri) {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            InputStream is = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

            String inputLine;
            String content = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                String cleaned = StAXUtil.cleanString(inputLine);
                content += inputLine + "\n";
                writer.write(cleaned);
            }

            bufferedReader.close();
            writer.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    abstract void parse(String startUrl, XMLEventReader reader);

    //
    void execute(List<String> startUrls) throws FileNotFoundException, XMLStreamException {
        if (startUrls != null) {
            for (String url : startUrls) {
                getContent(DEFAULT_PARENT_PATH + "/page", url);
                XMLEventReader reader = StAXUtil.getEventReader(DEFAULT_PARENT_PATH + "/page");
                parse(url, reader);
            } // end for
        }// end if
    }

    //
    void execute(List<String> startUrls, ParserHandler handler) {
        if (startUrls != null) {
            for (String url : startUrls) {
                getContent(url, DEFAULT_PARENT_PATH + "/page");
                try {
                    handler.onParse(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end for
        }// end if
    }
//
//    public void close() {
//        this.webDriver.quit();
//    }
//
//    public List<WebElement> extractElements(String expr) throws NoSuchElementException {
//        return this.webDriver.findElements(By.xpath(expr));
//    }
//
//    public WebElement extractElement(String expr) throws NoSuchElementException {
//        return this.webDriver.findElement(By.xpath(expr));
//    }
}
