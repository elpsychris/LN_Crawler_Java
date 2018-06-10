package crawler.spider;

import crawler.model.Project;
import crawler.utils.StAXUtil;
import sun.security.x509.OtherName;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectSpider extends Spider {
    private String xpathProjectElement = "//main[@class='sect-body listext-table widthfluid clear']/article[not(contains(@class,'top'))]/div[1]/a";
    private String xpathNextPageElement = "//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1]";
    private String xpathSynopsis = "//div[@class='listall_summary none force-block-l']/p";
    private String xpathAuthor = "//div[@class = 'ln_info-item clear']/span[text()='Tác giả']/following-sibling::span/a";

    private List<Project> result = new ArrayList<Project>();
    private Map<String, String> projectDetailList = new HashMap<String, String>();

    private String nextPage;

    boolean inProjectList = false;
    boolean inProjectRow = false;
    boolean inFirstDiv = false;

    boolean inPaginationWrap = false;
    boolean inCurrentPageDiv = false;

    boolean inSynopsisDiv = false;

    private String currentE = "";

    int count = 0;

    void parse(String url, XMLEventReader reader) {
        parseContent(reader);

        setStartUrls(projectDetailList, new ParserHandler() {
            @Override
            public void onParse(Map.Entry page, XMLEventReader reader) throws InterruptedException {
                Project project = new Project();
                project.setProjectName((String) page.getKey());

                parseProjectDetailContent(reader, project);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("SML r!!!");
            }
        });

        if (nextPage != null) {
            this.projectDetailList = new HashMap<String, String>();
            System.out.println("NEXT PAGE==================\n" + nextPage);
            setStartUrls(nextPage);
        }
    }

    private void parseProjectDetailContent(XMLEventReader reader, Project project) {
        boolean cont = true;

        while (reader.hasNext() && cont) {
            try {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startE = (StartElement) event;
                    parseProjectDetail(reader, startE, project);

                }
            } catch (XMLStreamException e) {
//                e.printStackTrace();
            } catch (NullPointerException e) {
//                e.printStackTrace();
                cont = false;
            }
        }
        System.out.println(project.toString());
        System.out.println("================================");
    }
    private String curInfo = null;

    private void parseProjectDetail(XMLEventReader reader, StartElement startE, Project project) throws XMLStreamException {
        parseSynopsis(reader, startE, project);
        String otherName = null;
        String author = null;

        if (startE.getName().toString().equals("span")
                && StAXUtil.extractAttr(startE, "class").contains("ln_info-name")) {
            curInfo = reader.getElementText();
            currentE = "";
        }


        if (startE.getName().toString().equals("a")
                && currentE.equals("span")) {
            if (curInfo.equals("Tác giả")) {
                author = reader.getElementText();
            }
        }

        if (startE.getName().toString().equals("span")
                && currentE.equals("span")) {
            if (curInfo.equals("Tên khác")) {
                otherName = reader.getElementText();
            }
        } else if (!startE.getName().toString().equals("span")) {
            currentE = "";
        }

        if (startE.getName().toString().equals("span")
                && curInfo != null
                && StAXUtil.extractAttr(startE, "class").contains("ln_info-value")) {
            currentE = "span";
        }

        if (otherName != null) {
            if (project.getProjectNameOthers() != null) {
                otherName = project.getProjectNameOthers() + ";" + otherName;
            }

            project.setProjectNameOthers(otherName);
        }

        if (author != null) {
            System.out.println("Author: " + author);
        }
    }


    private void parseSynopsis(XMLEventReader reader, StartElement startE, Project project) throws XMLStreamException {
        if (startE.getName().toString().equals("div")
                && StAXUtil.extractAttr(startE, "class").equals("listall_summary none force-block-l")) {
            inSynopsisDiv = true;
        }

        if (startE.getName().toString().equals("p") && inSynopsisDiv) {
            String curSyn = project.getProjectSynopsis();
            if (curSyn == null) {
                curSyn = "";
            }
            String line = reader.getElementText().trim();
            if (!line.isEmpty()) {
                curSyn = curSyn + "\n" + line;
            }
            project.setProjectSynopsis(curSyn);
            currentE = "p";
        } else if (currentE.equals("p")) {
            inSynopsisDiv = false;
            currentE = "";
        }
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
            String link = StAXUtil.extractAttr(startE, "href");
            String name = reader.getElementText();

            projectDetailList.put(name, link);
            count++;

            inFirstDiv = false;
            inProjectRow = false;
        }
    }

    private void parsePagination(XMLEventReader reader, StartElement startE) throws XMLStreamException {
        if (startE.getName().toString().equals("div")
                && StAXUtil.extractAttr(startE, "class").equals("pagination_wrap")) {
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
    }
}
