package crawler.spider;


import crawler.repository.ProjectRepo;
import crawler.utils.CrawlerUtils;
import crawler.utils.StAXUtils;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

abstract class Spider {
    final String DEFAULT_PARENT_PATH = "src/main/java/crawl_temp";
    ProjectRepo projectRepo = null;

    public Spider(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    private String document = "";

    interface ParserHandler {
        public void onParse(String page, InputStream documentInputStream) throws InterruptedException;

        public void onError(Exception e);
    }


    public void setStartUrls(List<String> startUrls, Pattern pattern) {
        try {
            execute(startUrls, pattern);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setStartUrls(List<String> startUrls, ParserHandler handler, Pattern pattern) {
        try {
            execute(startUrls, handler, pattern);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStartUrls(String startUrl, Pattern pattern) {
        List<String> startUrls = new ArrayList<String>();
        startUrls.add(startUrl);
        setStartUrls(startUrls, pattern);
    }

    //
//    private void getContent(String filePath, String uri) throws InterruptedException {
//        Writer writer = null;
    private void getContent(String uri, Pattern pattern) throws InterruptedException {
        this.document = "";
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
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                if (inputLine.trim().isEmpty()) {
                    continue;
                }
                document = document + StAXUtils.cleanString(inputLine) + "\n";
            }
            document = StAXUtils.cleanDocument(document);
            if (pattern != null) {
                document = CrawlerUtils.getSubContent(pattern, document);
            }

//            writer.write(document);

            bufferedReader.close();
//            writer.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    abstract void parse(String startUrl, InputStream document);

    //
    private void execute(List<String> startUrls, Pattern pattern) throws FileNotFoundException, XMLStreamException, InterruptedException, UnsupportedEncodingException {
        if (startUrls != null) {
            for (String url : startUrls) {
                getContent(url, pattern);
                ByteArrayInputStream documentInputStream = new ByteArrayInputStream(document.getBytes());
//                XMLEventReader reader = StAXUtils.getEventReader(byteInputStream);
                parse(url, documentInputStream);
            } // end for
        }// end if
    }

    //
    private void execute(List<String> startUrls, ParserHandler handler, Pattern pattern) throws InterruptedException {
        if (startUrls != null) {
            for (String page : startUrls) {
                getContent(page, pattern);
                try {
                    ByteArrayInputStream documentInputStream = new ByteArrayInputStream(document.getBytes());
//                    XMLEventReader reader = StAXUtils.getEventReader(byteInputStream);
                    handler.onParse(page, documentInputStream);
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
