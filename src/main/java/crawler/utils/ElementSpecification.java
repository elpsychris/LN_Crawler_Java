package crawler.utils;

import javax.xml.stream.events.StartElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementSpecification {
    private String attrKeyCriteria;
    private String attrValCriteria;
    private String nameCriteria;

    private String[] attributeList;
    private List<Map<String, String>> attributeResult;
    private String elementContent;

    public ElementSpecification() {
        super();
    }

    public ElementSpecification(String attrKeyCriteria, String attrValCriteria, String nameCriteria) {
        this.attrKeyCriteria = attrKeyCriteria;
        this.attrValCriteria = attrValCriteria;
        this.nameCriteria = nameCriteria;
    }

    public ElementSpecification(String nameCriteria) {
        this.nameCriteria = nameCriteria;
    }

    public List<Map<String, String>> getAttributeResult() {
        return attributeResult;
    }

    public void setAttributeResult(List<Map<String, String>> attributeResult) {
        this.attributeResult = attributeResult;
    }

    public String getElementContent() {
        return elementContent;
    }

    public void setElementContent(String elementContent) {
        this.elementContent = elementContent;
    }

    public String getAttrKeyCriteria() {
        return attrKeyCriteria;
    }

    public void setAttrKeyCriteria(String attrKeyCriteria) {
        this.attrKeyCriteria = attrKeyCriteria;
    }

    public String getAttrValCriteria() {
        return attrValCriteria;
    }

    public void setAttrValCriteria(String attrValCriteria) {
        this.attrValCriteria = attrValCriteria;
    }

    public String getNameCriteria() {
        return nameCriteria;
    }

    public void setNameCriteria(String nameCriteria) {
        this.nameCriteria = nameCriteria;
    }

    public String[] getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(String[] attributeList) {
        this.attributeList = attributeList;
    }
}