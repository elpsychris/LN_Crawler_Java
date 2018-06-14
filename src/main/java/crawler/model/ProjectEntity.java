package crawler.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Project", schema = "dbo", catalog = "NU_DB")
public class ProjectEntity {
    private int projectId;
    private String projectName;
    private String projectAlterName;
    private Integer projectOriginId;
    private Integer projectYear;
    private String projectAuthor;
    private String projectIllustrator;
    private String projectSynopsis;
    private String projectHash;
    private String projectTag;
    private Integer projectPublisher;
    private Integer projectView;
    private Integer projectPoint;
    private String projectGenre;
    private Date projectLastUpdate;
    private Integer projectTotalUpdate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "project_alter_name")
    public String getProjectAlterName() {
        return projectAlterName;
    }

    public void setProjectAlterName(String projectAlterName) {
        this.projectAlterName = projectAlterName;
    }

    @Basic
    @Column(name = "project_origin_id")
    public Integer getProjectOriginId() {
        return projectOriginId;
    }

    public void setProjectOriginId(Integer projectOriginId) {
        this.projectOriginId = projectOriginId;
    }

    @Basic
    @Column(name = "project_year")
    public Integer getProjectYear() {
        return projectYear;
    }

    public void setProjectYear(Integer projectYear) {
        this.projectYear = projectYear;
    }

    @Basic
    @Column(name = "project_author")
    public String getProjectAuthor() {
        return projectAuthor;
    }

    public void setProjectAuthor(String projectAuthor) {
        this.projectAuthor = projectAuthor;
    }

    @Basic
    @Column(name = "project_illustrator")
    public String getProjectIllustrator() {
        return projectIllustrator;
    }

    public void setProjectIllustrator(String projectIllustrator) {
        this.projectIllustrator = projectIllustrator;
    }

    @Basic
    @Column(name = "project_synopsis")
    public String getProjectSynopsis() {
        return projectSynopsis;
    }

    public void setProjectSynopsis(String projectSynopsis) {
        this.projectSynopsis = projectSynopsis;
    }

    @Basic
    @Column(name = "project_hash")
    public String getProjectHash() {
        return projectHash;
    }

    public void setProjectHash(String projectHash) {
        this.projectHash = projectHash;
    }

    @Basic
    @Column(name = "project_tag")
    public String getProjectTag() {
        return projectTag;
    }

    public void setProjectTag(String projectTag) {
        this.projectTag = projectTag;
    }

    @Basic
    @Column(name = "project_publisher")
    public Integer getProjectPublisher() {
        return projectPublisher;
    }

    public void setProjectPublisher(Integer projectPublisher) {
        this.projectPublisher = projectPublisher;
    }

    @Basic
    @Column(name = "project_view")
    public Integer getProjectView() {
        return projectView;
    }

    public void setProjectView(Integer projectView) {
        this.projectView = projectView;
    }

    @Basic
    @Column(name = "project_point")
    public Integer getProjectPoint() {
        return projectPoint;
    }

    public void setProjectPoint(Integer projectPoint) {
        this.projectPoint = projectPoint;
    }

    @Basic
    @Column(name = "project_genre")
    public String getProjectGenre() {
        return projectGenre;
    }

    public void setProjectGenre(String projectGenre) {
        this.projectGenre = projectGenre;
    }

    @Basic
    @Column(name = "project_last_update")
    public Date getProjectLastUpdate() {
        return projectLastUpdate;
    }

    public void setProjectLastUpdate(Date projectLastUpdate) {
        this.projectLastUpdate = projectLastUpdate;
    }

    @Basic
    @Column(name = "project_total_update")
    public Integer getProjectTotalUpdate() {
        return projectTotalUpdate;
    }

    public void setProjectTotalUpdate(Integer projectTotalUpdate) {
        this.projectTotalUpdate = projectTotalUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectEntity that = (ProjectEntity) o;

        if (projectId != that.projectId) return false;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        if (projectAlterName != null ? !projectAlterName.equals(that.projectAlterName) : that.projectAlterName != null)
            return false;
        if (projectOriginId != null ? !projectOriginId.equals(that.projectOriginId) : that.projectOriginId != null)
            return false;
        if (projectYear != null ? !projectYear.equals(that.projectYear) : that.projectYear != null) return false;
        if (projectAuthor != null ? !projectAuthor.equals(that.projectAuthor) : that.projectAuthor != null)
            return false;
        if (projectIllustrator != null ? !projectIllustrator.equals(that.projectIllustrator) : that.projectIllustrator != null)
            return false;
        if (projectSynopsis != null ? !projectSynopsis.equals(that.projectSynopsis) : that.projectSynopsis != null)
            return false;
        if (projectHash != null ? !projectHash.equals(that.projectHash) : that.projectHash != null) return false;
        if (projectTag != null ? !projectTag.equals(that.projectTag) : that.projectTag != null) return false;
        if (projectPublisher != null ? !projectPublisher.equals(that.projectPublisher) : that.projectPublisher != null)
            return false;
        if (projectView != null ? !projectView.equals(that.projectView) : that.projectView != null) return false;
        if (projectPoint != null ? !projectPoint.equals(that.projectPoint) : that.projectPoint != null) return false;
        if (projectGenre != null ? !projectGenre.equals(that.projectGenre) : that.projectGenre != null) return false;
        if (projectLastUpdate != null ? !projectLastUpdate.equals(that.projectLastUpdate) : that.projectLastUpdate != null)
            return false;
        if (projectTotalUpdate != null ? !projectTotalUpdate.equals(that.projectTotalUpdate) : that.projectTotalUpdate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectAlterName != null ? projectAlterName.hashCode() : 0);
        result = 31 * result + (projectOriginId != null ? projectOriginId.hashCode() : 0);
        result = 31 * result + (projectYear != null ? projectYear.hashCode() : 0);
        result = 31 * result + (projectAuthor != null ? projectAuthor.hashCode() : 0);
        result = 31 * result + (projectIllustrator != null ? projectIllustrator.hashCode() : 0);
        result = 31 * result + (projectSynopsis != null ? projectSynopsis.hashCode() : 0);
        result = 31 * result + (projectHash != null ? projectHash.hashCode() : 0);
        result = 31 * result + (projectTag != null ? projectTag.hashCode() : 0);
        result = 31 * result + (projectPublisher != null ? projectPublisher.hashCode() : 0);
        result = 31 * result + (projectView != null ? projectView.hashCode() : 0);
        result = 31 * result + (projectPoint != null ? projectPoint.hashCode() : 0);
        result = 31 * result + (projectGenre != null ? projectGenre.hashCode() : 0);
        result = 31 * result + (projectLastUpdate != null ? projectLastUpdate.hashCode() : 0);
        result = 31 * result + (projectTotalUpdate != null ? projectTotalUpdate.hashCode() : 0);
        return result;
    }
}
