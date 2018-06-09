package crawler.spider;

import crawler.utils.StAXUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;

public class ProjectSpider extends Spider {
    private String xpathProjectElement = "//main[@class='sect-body listext-table widthfluid clear']/article[not(contains(@class,'top'))]/div[1]/a";
    private String xpathNextPageElement = "//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1]";
    private String xpathSynopsis = "//div[@class='listall_summary none force-block-l']/p";
    private String xpathAuthor = "//div[@class = 'ln_info-item clear']/span[text()='Tác giả']/following-sibling::span/a";

    private String nextPage;

    boolean inProjectList = false;
    boolean inProjectRow = false;
    boolean inFirstDiv = false;

    boolean inPaginationWrap = false;
    boolean inCurrentPageDiv = false;

    int count = 0;

    void parse(String url, XMLEventReader reader) {
        parseContent(reader);
    }

    private void parseProjectItem(XMLEventReader reader, StartElement startE) throws XMLStreamException {
        if (startE.getName().toString().equals("main")) {
            if (StAXUtil.extractAttr(startE, "class").equals("sect-body listext-table widthfluid clear")) {
                inProjectList = true;
            } // end check inProjectList
        } // end check name

        if (inProjectList && startE.getName().toString().equals("article")) {
            if (!StAXUtil.extractAttr(startE, "class").contains("top")) {
                inProjectRow = true;
            }// check if not header article
        }

        if (inProjectRow && startE.getName().toString().equals("div")) {
            inFirstDiv = true;
        }

        if (inFirstDiv && startE.getName().toString().equals("a")) {
            System.out.println(StAXUtil.extractAttr(startE, "href"));
            String result = reader.getElementText();
            System.out.println("Name: " + result);
            count++;

            inFirstDiv = false;
            inProjectRow = false;
        }
    }

    private void parsePagination(XMLEventReader reader, StartElement startE) throws XMLStreamException {
        if (startE.getName().toString().equals("div")
                && StAXUtil.extractAttr(startE,"class").equals("pagination_wrap")){
            inPaginationWrap = true;
        }

        if (startE.getName().toString().equals("a") && inCurrentPageDiv) {
            inCurrentPageDiv = false;
            inPaginationWrap = false;
            nextPage = StAXUtil.extractAttr(startE, "href");
        }

        if (startE.getName().toString().equals("a")
                && StAXUtil.extractAttr(startE, "class").contains("current")
                && inPaginationWrap) {
            inCurrentPageDiv = true;
        }
    }
    private void parseContent(XMLEventReader reader) {
        boolean cont = true;
        while (reader.hasNext() && cont) {
            try {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startE = (StartElement) event;

                    parseProjectItem(reader, startE);
                    parsePagination(reader, startE);
                }
            } catch (XMLStreamException e) {
            } catch (NullPointerException e) {
                cont = false;
            }
        }

        if (nextPage != null) {
            System.out.println("NEXT PAGE==================\n" + nextPage);
            setStartUrls(nextPage);
        }
    }
}