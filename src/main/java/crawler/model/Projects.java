package crawler.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "nextList",
        "projects"
})
@XmlRootElement(name = "project-page")
public class Projects {

    @XmlElement (name = "next-page")
    private String nextList = null;
    @XmlElementWrapper(name = "projects")
    @XmlElement(required = true, name = "project")
    private List<ProjectEntity> projects;

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
