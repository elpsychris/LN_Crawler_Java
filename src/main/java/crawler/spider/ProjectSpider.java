package crawler.spider;

import crawler.model.ProjectEntity;
import crawler.repository.ProjectRepo;
import crawler.utils.CrawlerUtils;
import crawler.utils.ElementSpecification;
import crawler.utils.StAXUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectSpider extends Spider {
    private String xpathProjectElement = "//main[@class='sect-body listext-table widthfluid clear']/article[not(contains(@class,'top'))]/div[1]/a";
    private String xpathNextPageElement = "//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1]";
    private String xpathSynopsis = "//div[@class='listall_summary none force-block-l']/p";
    private String xpathAuthor = "//div[@class = 'ln_info-item clear']/span[text()='Tác giả']/following-sibling::span/a";

    private List<ProjectEntity> result = new ArrayList<ProjectEntity>();
    private List<String> projectDetailList = new ArrayList<String>();

    private String nextPage;

    boolean inProjectList = false;
    boolean inProjectRow = false;
    boolean inFirstDiv = false;

    boolean inPaginationWrap = false;
    boolean inCurrentPageDiv = false;

    boolean inSynopsisDiv = false;

    private String currentE = "";

    int count = 0;

    public ProjectSpider(ProjectRepo projectRepo) {
        super(projectRepo);
    }

    void parse(String url, InputStream documentInputStream) {
        try {
            XMLEventReader reader = StAXUtils.getEventReader(documentInputStream);
            parseContent(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }


        setStartUrls(projectDetailList, new ParserHandler() {


            @Override
            public void onParse(String page, InputStream documentInputStream) throws InterruptedException {
                System.out.println(page);
                try {
                    XMLEventReader reader = StAXUtils.getEventReader(documentInputStream);
                    parseProjectDetail(reader);
                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
                } catch (XMLStreamException e) {
//                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                System.out.println("SML r!!!");
            }
        }, null);

        if (nextPage != null) {
            this.projectDetailList = new ArrayList<String>();
            System.out.println("NEXT PAGE==================\n" + nextPage);
            setStartUrls(nextPage, null);
        }
    }


    private void parseContent(XMLEventReader reader) {
//        boolean cont = true;
//        while (reader.hasNext() && cont) {
//            try {
//                XMLEvent event = reader.nextEvent();
//
//                if (event.isStartElement()) {
//                    StartElement startE = (StartElement) event;
//
//                    parseProjectItem(reader, startE);
//                    parsePagination(reader, startE);
//                }
//            } catch (XMLStreamException e) {
//            } catch (NullPointerException e) {
//                cont = false;
//            }
//        }
        boolean cont = true;
        while (cont && reader.hasNext()) {
            try {
                if (reader.hasNext()) {
                    parseProjectItem(reader);
                }
            } catch (NullPointerException e) {
                cont = false;
            }
        }
    }

    private void parseProjectItem(XMLEventReader reader) {
        ElementSpecification projectItemParentCri = new ElementSpecification("class", "listext-item clear", "article");

        ElementSpecification projectItemCri = new ElementSpecification("a");
        String[] attrSpecs = new String[]{"href"};
        projectItemCri.setAttributeList(attrSpecs);

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, null);
            System.out.println(result.getElementContent());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> attrList = result.getAttributeResult();
        if (attrList.size() > 0) {
            Map<String, String> projectLink = attrList.get(0);
            this.projectDetailList.add(projectLink.get("href"));
        }
    }

    private void parseProjectDetail(XMLEventReader reader) {
        String projectTitle = null;
        String projectSynopsis = null;
        ProjectEntity newProject = new ProjectEntity();
        // Parse project title
        projectTitle = parseProjectTitle(reader);
        System.out.println(projectTitle);
        newProject.setProjectName(projectTitle);

        //Parse project synopsis
        projectSynopsis = parseProjectSynopsis(reader);
        System.out.println(projectSynopsis);
        newProject.setProjectSynopsis(projectSynopsis);

        //Parse project info
        parseProjectInfo(reader, false);
        String author = parseProjectInfo(reader, true);
        newProject.setProjectAuthor(author);
        System.out.println(parseProjectInfo(reader, true));
        System.out.println(parseProjectInfo(reader, true));
        String sView = parseProjectInfo(reader, false);
        int view = 0;
        try {
            view = Integer.parseInt(sView);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            newProject.setProjectView(view);
        }

        System.out.println(parseProjectInfo(reader, false));


        System.out.println(parseProjectInfo(reader, true));
        String alterName = parseProjectInfo(reader, false);
        newProject.setProjectAlterName(alterName);
        newProject.setProjectHash("asas");

        this.projectRepo.add(newProject);

        //Parse volume info
        ElementSpecification stopParseVolSpec = new ElementSpecification("class", "basic-section mobile-view clear", "section");
        boolean cont = true;
        while (cont) {
            cont = parseVolumeInfo(reader, stopParseVolSpec);
        }

    }

    private String parseProjectTitle(XMLEventReader reader) {
        ElementSpecification projectItemParentCri = new ElementSpecification("class", "listall_title animation fade-in-up", "h2");
        ElementSpecification projectItemCri = new ElementSpecification("a");
        ElementSpecification stopSign = new ElementSpecification("class", "listall_img col-12 col-3-l", "div");

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }

        return result.getElementContent();
    }

    private String parseProjectSynopsis(XMLEventReader reader) {
        String synopsis = "";

        ElementSpecification projectItemParentCri = new ElementSpecification("class", "listall_summary none force-block-l", "div");
        ElementSpecification projectItemCri = new ElementSpecification("p");
        ElementSpecification stopSign = new ElementSpecification("class", "reg-part clear", "div");

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }

        synopsis = result.getElementContent();
        return synopsis;
    }

    private String parseProjectInfo(XMLEventReader reader, boolean isA) {
        String infoVal = "";

        ElementSpecification projectItemParentCri = new ElementSpecification("class", "ln_info-item clear", "div");
        ElementSpecification projectItemCri;
        ElementSpecification stopSign = projectItemParentCri;

        if (isA) {
            projectItemCri = new ElementSpecification("a");
            String[] attrList = new String[] {"href"};
            projectItemCri.setAttributeList(attrList);
        } else {
            projectItemCri = new ElementSpecification("class", "ln_info-value col-7", "span");
        }
        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }

        infoVal = result.getElementContent();
        return infoVal;
    }

    private boolean parseVolumeInfo(XMLEventReader reader, ElementSpecification stopSpec) {
        String volTitle = null;
        ElementSpecification projectItemGrandParentCri = new ElementSpecification("class", "mobile-toggle", "div");
        ElementSpecification projectItemParentCri = new ElementSpecification("class", "inline-block none-l mobile-toggle_header", "header");
        ElementSpecification projectItemCri = new ElementSpecification("a");
        ElementSpecification stopSign = new ElementSpecification("class", "sect-header vol-header none block-l", "header");

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemGrandParentCri);
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }
        System.out.println(result.getElementContent() + "===========");
        boolean cont = true;
        while (cont) {
            parseChapterInfo(reader);
            parseChapterDateInfo(reader);

            ElementSpecification beginChapter = new ElementSpecification("class", "listext-item clear", "article");
            ElementSpecification beginNewVol = projectItemGrandParentCri;

            XMLEvent nextEvent = null;
            boolean skipCont = true;
            while (skipCont) {
                try {
                    nextEvent = reader.peek();
                    if (nextEvent.isStartElement()) {
                        StartElement startE = nextEvent.asStartElement();
                        if (CrawlerUtils.checkCriteria(beginNewVol, startE)) {
                            cont = false;
                            skipCont = false;
                        } else if (CrawlerUtils.checkCriteria(beginChapter, startE)) {
                            skipCont = false;
                        } else if (CrawlerUtils.checkCriteria(stopSpec, startE)) {
                            return false;
                        } else {
                            reader.nextEvent();
                        }
                    } else {
                        reader.nextEvent();
                    }
                } catch (XMLStreamException e) {
//                e.printStackTrace();
                } catch (NullPointerException e) {
                    cont = false;
                    skipCont = false;
                    return false;
                }
            }

        }
        return true;
    }

    private String parseChapterInfo(XMLEventReader reader) {
        ElementSpecification projectItemParentCri = new ElementSpecification("class", "listext-item clear", "article");
        ElementSpecification projectItemCri = new ElementSpecification("a");
        ElementSpecification stopSign = new ElementSpecification("class",
                "listext_attr l_c-v_m-time none inline-m col-3-m col-4-l col-3-xl listext_attr listext_attr",
                "div");

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemParentCri);
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }
        System.out.print("-" + result.getElementContent().trim());
        return null;
    }

    private String parseChapterDateInfo(XMLEventReader reader) {
        ElementSpecification projectItemCri = new ElementSpecification("class", "listext_attr l_c-v_m-time none inline-m col-3-m col-4-l col-3-xl listext_attr listext_attr", "div");
        ElementSpecification stopSign = new ElementSpecification("class",
                "listext-item clear",
                "article");

        List<ElementSpecification> criteriaList = new ArrayList<ElementSpecification>();
        criteriaList.add(projectItemCri);

        ElementSpecification result = null;
        try {
            result = CrawlerUtils.getDataFromElement(reader, criteriaList, false, stopSign);
        } catch (XMLStreamException e) {
        }
        System.out.println("|" + result.getElementContent().trim());
        return null;
    }


    //    private void parseProjectDetailContent(XMLEventReader reader, Project project) {
//        boolean cont = true;
//
//        while (reader.hasNext() && cont) {
//            try {
//                XMLEvent event = reader.nextEvent();
//
//                if (event.isStartElement()) {
//                    StartElement startE = (StartElement) event;
//                    parseProjectDetail(reader, startE, project);
//
//                }
//            } catch (XMLStreamException e) {
////                e.printStackTrace();
//            } catch (NullPointerException e) {
////                e.printStackTrace();
//                cont = false;
//            }
//        }
//        System.out.println(project.toString());
//        System.out.println("================================");
//    }
//
//    private String curInfo = null;
//
//    private void parseProjectDetail(XMLEventReader reader, StartElement startE, Project project) throws XMLStreamException {
//        parseSynopsis(reader, startE, project);
//        String otherName = null;
//        String author = null;
//
//        if (startE.getName().toString().equals("span")
//                && StAXUtils.extractAttr(startE, "class").contains("ln_info-name")) {
//            curInfo = reader.getElementText();
//            currentE = "";
//        }
//
//
//        if (startE.getName().toString().equals("a")
//                && currentE.equals("span")) {
//            if (curInfo.equals("Tác giả")) {
//                author = reader.getElementText();
//            }
//        }
//
//        if (startE.getName().toString().equals("span")
//                && currentE.equals("span")) {
//            if (curInfo.equals("Tên khác")) {
//                otherName = reader.getElementText();
//            }
//        } else if (!startE.getName().toString().equals("span")) {
//            currentE = "";
//        }
//
//        if (startE.getName().toString().equals("span")
//                && curInfo != null
//                && StAXUtils.extractAttr(startE, "class").contains("ln_info-value")) {
//            currentE = "span";
//        }
//
//        if (otherName != null) {
//            if (project.getProjectNameOthers() != null) {
//                otherName = project.getProjectNameOthers() + ";" + otherName;
//            }
//
//            project.setProjectNameOthers(otherName);
//        }
//
//        if (author != null) {
//            System.out.println("Author: " + author);
//        }
//    }
//
//
//    private void parseSynopsis(XMLEventReader reader, StartElement startE, Project project) throws XMLStreamException {
//        if (startE.getName().toString().equals("div")
//                && StAXUtils.extractAttr(startE, "class").equals("listall_summary none force-block-l")) {
//            inSynopsisDiv = true;
//        }
//
//        if (startE.getName().toString().equals("p") && inSynopsisDiv) {
//            String curSyn = project.getProjectSynopsis();
//            if (curSyn == null) {
//                curSyn = "";
//            }
//            String line = reader.getElementText().trim();
//            if (!line.isEmpty()) {
//                curSyn = curSyn + "\n" + line;
//            }
//            project.setProjectSynopsis(curSyn);
//            currentE = "p";
//        } else if (currentE.equals("p")) {
//            inSynopsisDiv = false;
//            currentE = "";
//        }
//    }
//
//    private void parseProjectItem(XMLEventReader reader, StartElement startE) throws XMLStreamException {
//        if (startE.getName().toString().equals("main")) {
//            if (StAXUtils.extractAttr(startE, "class").equals("sect-body listext-table widthfluid clear")) {
//                inProjectList = true;
//            } // end check inProjectList
//        } // end check name
//
//        if (inProjectList && startE.getName().toString().equals("article")) {
//            if (!StAXUtils.extractAttr(startE, "class").contains("top")) {
//                inProjectRow = true;
//            }// check if not header article
//        }
//
//        if (inProjectRow && startE.getName().toString().equals("div")) {
//            inFirstDiv = true;
//        }
//
//        if (inFirstDiv && startE.getName().toString().equals("a")) {
//            String link = StAXUtils.extractAttr(startE, "href");
//            String name = reader.getElementText();
//
//            projectDetailList.put(name, link);
//            count++;
//
//            inFirstDiv = false;
//            inProjectRow = false;
//        }
//    }
//
//    private void parsePagination(XMLEventReader reader, StartElement startE) throws XMLStreamException {
//        if (startE.getName().toString().equals("div")
//                && StAXUtils.extractAttr(startE, "class").equals("pagination_wrap")) {
//            inPaginationWrap = true;
//        }
//
//        if (startE.getName().toString().equals("a") && inCurrentPageDiv) {
//            inCurrentPageDiv = false;
//            inPaginationWrap = false;
//            nextPage = StAXUtils.extractAttr(startE, "href");
//        }
//
//        if (startE.getName().toString().equals("a")
//                && StAXUtils.extractAttr(startE, "class").contains("current")
//                && inPaginationWrap) {
//            inCurrentPageDiv = true;
//        }
//    }
}
