package crawler.spider;


import crawler.utils.StAXUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Spider {
    final String DEFAULT_PARENT_PATH = "src/main/java/crawl_temp";

    interface ParserHandler {
        public void onParse(Map.Entry<String, String> page, XMLEventReader reader) throws InterruptedException;

        public void onError(Exception e);
    }


    public void setStartUrls(List<String> startUrls) {
        try {
            execute(startUrls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStartUrls(Map<String, String> startUrls, ParserHandler handler) {
        try {
            execute(startUrls, handler);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStartUrls(String startUrl) {
        List<String> startUrls = new ArrayList<String>();
        startUrls.add(startUrl);
        setStartUrls(startUrls);
    }

    //
    private void getContent(String filePath, String uri) throws InterruptedException {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setDefaultUseCaches(false);
            InputStream is = null;
            boolean cont = true;
            while (cont) {
                try {
                    is = connection.getInputStream();
                    cont = false;
                    Thread.sleep(1000);
                } catch (IOException e) {
                    System.out.println("Failed to connect to " + url);
                    System.out.println(e.getMessage());
                    connection.setReadTimeout(1000);
                    Thread.sleep(10000);
                    cont = true;
                    connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                }
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

            String inputLine;
            String document = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                if (inputLine.trim().isEmpty()) {
                    continue;
                }
                document = document + StAXUtil.cleanString(inputLine) + "\n";
            }
            document = StAXUtil.cleanDocument(document);
            writer.write(document);

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
    private void execute(List<String> startUrls) throws FileNotFoundException, XMLStreamException, InterruptedException {
        if (startUrls != null) {
            for (String url : startUrls) {
                getContent(DEFAULT_PARENT_PATH + "/page", url);
                XMLEventReader reader = StAXUtil.getEventReader(DEFAULT_PARENT_PATH + "/page");
                parse(url, reader);
            } // end for
        }// end if
    }

    //
    private void execute(Map<String, String> startUrls, ParserHandler handler) throws InterruptedException {
        if (startUrls != null) {
            for (Map.Entry<String, String> page : startUrls.entrySet()) {
                getContent(DEFAULT_PARENT_PATH + "/page", page.getValue());
                try {
                    XMLEventReader reader = StAXUtil.getEventReader(DEFAULT_PARENT_PATH + "/page");
                    handler.onParse(page, reader);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (XMLStreamException e) {
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
