package crawler.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Project {
    private int projectId;
    private String projectName;
    private String projectNameOthers;
    private Integer projectOriginId;
    private Timestamp projectPubTime;
    private Integer projectAuthorId;
    private Integer projectIllustratorId;
    private String projectSynopsis;
    private String projectHash;
    private String projectTag;
    private Integer projectPublisher;
    private Origin originByProjectOriginId;
    private Publisher publisherByProjectPublisher;

    @Id
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
    @Column(name = "project_name_others")
    public String getProjectNameOthers() {
        return projectNameOthers;
    }

    public void setProjectNameOthers(String projectNameOthers) {
        this.projectNameOthers = projectNameOthers;
    }

    @Basic
    @Column(name = "project_origin_id", insertable = false, updatable = false)
    public Integer getProjectOriginId() {
        return projectOriginId;
    }

    public void setProjectOriginId(Integer projectOriginId) {
        this.projectOriginId = projectOriginId;
    }

    @Basic
    @Column(name = "project_pub_time")
    public Timestamp getProjectPubTime() {
        return projectPubTime;
    }

    public void setProjectPubTime(Timestamp projectPubTime) {
        this.projectPubTime = projectPubTime;
    }

    @Basic
    @Column(name = "project_author_id", insertable = false, updatable = false)
    public Integer getProjectAuthorId() {
        return projectAuthorId;
    }

    public void setProjectAuthorId(Integer projectAuthorId) {
        this.projectAuthorId = projectAuthorId;
    }

    @Basic
    @Column(name = "project_illustrator_id", insertable = false, updatable = false)
    public Integer getProjectIllustratorId() {
        return projectIllustratorId;
    }

    public void setProjectIllustratorId(Integer projectIllustratorId) {
        this.projectIllustratorId = projectIllustratorId;
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
    @Column(name = "project_publisher", insertable = false, updatable = false)
    public Integer getProjectPublisher() {
        return projectPublisher;
    }

    public void setProjectPublisher(Integer projectPublisher) {
        this.projectPublisher = projectPublisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (projectId != project.projectId) return false;
        if (projectName != null ? !projectName.equals(project.projectName) : project.projectName != null) return false;
        if (projectNameOthers != null ? !projectNameOthers.equals(project.projectNameOthers) : project.projectNameOthers != null)
            return false;
        if (projectOriginId != null ? !projectOriginId.equals(project.projectOriginId) : project.projectOriginId != null)
            return false;
        if (projectPubTime != null ? !projectPubTime.equals(project.projectPubTime) : project.projectPubTime != null)
            return false;
        if (projectAuthorId != null ? !projectAuthorId.equals(project.projectAuthorId) : project.projectAuthorId != null)
            return false;
        if (projectIllustratorId != null ? !projectIllustratorId.equals(project.projectIllustratorId) : project.projectIllustratorId != null)
            return false;
        if (projectSynopsis != null ? !projectSynopsis.equals(project.projectSynopsis) : project.projectSynopsis != null)
            return false;
        if (projectHash != null ? !projectHash.equals(project.projectHash) : project.projectHash != null) return false;
        if (projectTag != null ? !projectTag.equals(project.projectTag) : project.projectTag != null) return false;
        if (projectPublisher != null ? !projectPublisher.equals(project.projectPublisher) : project.projectPublisher != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectNameOthers != null ? projectNameOthers.hashCode() : 0);
        result = 31 * result + (projectOriginId != null ? projectOriginId.hashCode() : 0);
        result = 31 * result + (projectPubTime != null ? projectPubTime.hashCode() : 0);
        result = 31 * result + (projectAuthorId != null ? projectAuthorId.hashCode() : 0);
        result = 31 * result + (projectIllustratorId != null ? projectIllustratorId.hashCode() : 0);
        result = 31 * result + (projectSynopsis != null ? projectSynopsis.hashCode() : 0);
        result = 31 * result + (projectHash != null ? projectHash.hashCode() : 0);
        result = 31 * result + (projectTag != null ? projectTag.hashCode() : 0);
        result = 31 * result + (projectPublisher != null ? projectPublisher.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "project_origin_id", referencedColumnName = "origin_id")
    public Origin getOriginByProjectOriginId() {
        return originByProjectOriginId;
    }

    public void setOriginByProjectOriginId(Origin originByProjectOriginId) {
        this.originByProjectOriginId = originByProjectOriginId;
    }

    @ManyToOne
    @JoinColumn(name = "project_publisher", referencedColumnName = "publisher_id")
    public Publisher getPublisherByProjectPublisher() {
        return publisherByProjectPublisher;
    }

    public void setPublisherByProjectPublisher(Publisher publisherByProjectPublisher) {
        this.publisherByProjectPublisher = publisherByProjectPublisher;
    }
}
