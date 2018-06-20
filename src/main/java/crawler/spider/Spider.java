package crawler.spider;


import crawler.model.Projects;
import crawler.repository.ProjectRepo;
import crawler.utils.*;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class Spider {
    final String DEFAULT_PARENT_PATH = "src/main/java/crawl_temp";
    ProjectRepo projectRepo = null;
    DOMResult result = null;

    public Spider() {
    }

    public Spider(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }


    interface ParserHandler {
        public void onParsed(DOMResult domResult);

        public void onError(Exception e);
    }

    public void start(String configPath, String xslPath, ParserHandler handler) throws IOException, TransformerException, InterruptedException {
        this.result = this.crawl(configPath, xslPath);
        handler.onParsed(this.result);
    }

    public void start(String configPath, String xslPath) throws IOException, TransformerException, InterruptedException, JAXBException {
        Runnable thread = () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TrAXUtils.stop();
        };
        Thread thread1 = new Thread(thread);
        thread1.start();
        this.result = this.crawl(configPath, xslPath);
        Projects projects = JAXBUtils.<Projects>xmlToObject(this.result, Projects.class);

//        handler.onParsed(this.result);
    }


//    public void setStartUrls(List<String> startUrls, Pattern pattern, String xslPath) {
//        try {
//            execute(startUrls, pattern, xslPath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

//    public void setStartUrls(List<String> startUrls, ParserHandler handler, Pattern pattern) {
//        try {
//            execute(startUrls, handler, pattern);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setStartUrls(String startUrl, Pattern pattern) {
//        List<String> startUrls = new ArrayList<String>();
//        startUrls.add(startUrl);
//        setStartUrls(startUrls, pattern);
//    }

    //
//    private void getContent(String filePath, String uri) throws InterruptedException {
//        Writer writer = null;
    private DOMResult crawl(String configPath, String xslPath) throws TransformerException, IOException, InterruptedException {
        StreamSource xsl = new StreamSource(xslPath);

        InputStream is = new FileInputStream(configPath);
        StreamSource config = new StreamSource(is);

        return TrAXUtils.transform(config, xsl);
    }

    //
//    abstract void parse(String startUrl, InputStream document);

    //
//    private void execute(List<String> startUrls, Pattern pattern) throws FileNotFoundException, XMLStreamException, InterruptedException, UnsupportedEncodingException {
//        if (startUrls != null) {
//            for (String url : startUrls) {
////                getContent(url, pattern);
////                ByteArrayInputStream documentInputStream = new ByteArrayInputStream(document.getBytes());
////                XMLEventReader reader = StAXUtils.getEventReader(byteInputStream);
//                parse(url, documentInputStream);
//            } // end for
//        }// end if
//    }

    //
//    private void execute(List<String> startUrls, ParserHandler handler, Pattern pattern, String configPath, String xslPath) throws InterruptedException, FileNotFoundException {
//        if (startUrls != null) {
//            for (String page : startUrls) {
//                getContent(page);
//                try {
//                    ByteArrayInputStream documentInputStream = new ByteArrayInputStream(document.getBytes());
////                    XMLEventReader reader = StAXUtils.getEventReader(byteInputStream);
//                    handler.onParse(page, documentInputStream);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } // end for
//        }// end if
//    }

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
