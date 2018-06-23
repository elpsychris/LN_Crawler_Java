package crawler.utils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerUtils {
    private static Logger logger = Logger.getLogger();

    public static boolean checkCriteria(ElementSpecification criteria, StartElement startE) {
        if (checkSibling(criteria, startE)) {
            if (criteria.getAttrKeyCriteria() == null) {
                return true;
            }
            String attrVal = StAXUtils.extractAttr(startE, criteria.getAttrKeyCriteria());
            if (attrVal.equals(criteria.getAttrValCriteria())) {
                return true;
            }// end if attr val
        } // end if name

        return false;
    }

    public static boolean checkSibling(ElementSpecification criteria, StartElement startE) {
        if (startE.getName().toString().equals(criteria.getNameCriteria())) {
            return true;
        }
        return false;
    }

    public static ElementSpecification getDataFromElement(XMLEventReader reader, List<ElementSpecification> criteriaList, boolean needContent, ElementSpecification stopSign) throws XMLStreamException {
        if (!reader.hasNext() || criteriaList == null || criteriaList.size() == 0) {
            return null;
        }
        ElementSpecification resultSpec = new ElementSpecification();

        String resultContent = "";
        String[] attrSpecs = null;

        boolean found = false;
        boolean cont = true;
        List attrResult = new ArrayList();
        int curCriIndex = 0;
        try {
            while (reader.hasNext() && curCriIndex < criteriaList.size() && cont) {
                ElementSpecification curCriteria = criteriaList.get(curCriIndex);
                try {
                    XMLEvent event = reader.nextEvent();
                    if (event.isStartElement()) {
                        StartElement startE = event.asStartElement();

                        if (checkCriteria(curCriteria, startE)) {
                            if (curCriIndex < criteriaList.size() - 1) {
                                curCriIndex++;
                            } else {
                                found = true;
                                if (resultContent.isEmpty()) {
                                    resultContent = reader.getElementText();
                                } else {
                                    resultContent = resultContent + "," + reader.getElementText();
                                }

                                if (attrSpecs == null) {
                                    attrSpecs = curCriteria.getAttributeList();
                                }

                                Map<String, String> resultMap = new HashMap<String, String>();
                                if (attrSpecs != null) {
                                    for (String attrSpec : attrSpecs) {
                                        String attrVal = StAXUtils.extractAttr(startE, attrSpec);
                                        resultMap.put(attrSpec, attrVal);
                                    }
                                }

                                attrResult.add(resultMap);
                            }
                        } else {
                            if (found) {
                                curCriIndex++;
                            }
                        }


                    }// end if check start element

                    if (found && stopSign != null) {
                        XMLEvent nextE = reader.peek();
                        if (nextE.isStartElement()) {
                            StartElement nextStartE = nextE.asStartElement();
                            if (checkCriteria(stopSign, nextStartE)){
                                cont = false;
                            }
                        }
                    }

                } catch (XMLStreamException ex) {
//                    ex.printStackTrace();
                    logger.log(Logger.LOG_LEVEL.WARNING,  ex);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        resultSpec.setAttributeResult(attrResult);
        resultSpec.setElementContent(resultContent);

        return resultSpec;
    }

    public static String getSubContent(Pattern pattern, String mainContent) {
        if (pattern == null) {
            return "";
        }
        Matcher matcher = pattern.matcher(mainContent);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group(0);
    }
}
