package crawler.spider;

import crawler.utils.StAXUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ProjectSpider extends Spider {
    private String xpathProjectElement = "//main[@class='sect-body listext-table widthfluid clear']/article[not(contains(@class,'top'))]/div[1]/a";
    private String xpathNextPageElement = "//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1]";
    private String xpathSynopsis = "//div[@class='listall_summary none force-block-l']/p";
    private String xpathAuthor = "//div[@class = 'ln_info-item clear']/span[text()='Tác giả']/following-sibling::span/a";

    boolean inProjectList = false;
    boolean inProjectRow = false;

    void parse(String url, XMLEventReader reader) {
        while (reader.hasNext()) {
            try {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    System.out.println("Found a start Element");
                    StartElement startE = (StartElement) event;
                    System.out.println(startE.getName());

                    if (startE.getName().toString().equals("main")) {
                        if (StAXUtil.extractAttr(startE, "class").equals("sect-body listext-table widthfluid clear")) {
                            inProjectList = true;
                        } // end check inProjectList
                    } // end check name

                    if (inProjectList && startE.getName().toString().equals("article")) {
                        if (!StAXUtil.extractAttr(startE,"class").contains("top")) {
                            inProjectRow = true;
                        }// check if not header article
                    }
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }


    }
}
