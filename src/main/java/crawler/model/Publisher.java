package crawler.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Publisher {
    private int publisherId;
    private String publisherName;
    private Integer originId;
    private String description;
    private Collection<Project> projectsByPublisherId;

    @Id
    @Column(name = "publisher_id")
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "publisher_name")
    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Basic
    @Column(name = "origin_id")
    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;

        if (publisherId != publisher.publisherId) return false;
        if (publisherName != null ? !publisherName.equals(publisher.publisherName) : publisher.publisherName != null)
            return false;
        if (originId != null ? !originId.equals(publisher.originId) : publisher.originId != null) return false;
        if (description != null ? !description.equals(publisher.description) : publisher.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisherId;
        result = 31 * result + (publisherName != null ? publisherName.hashCode() : 0);
        result = 31 * result + (originId != null ? originId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "publisherByProjectPublisher")
    public Collection<Project> getProjectsByPublisherId() {
        return projectsByPublisherId;
    }

    public void setProjectsByPublisherId(Collection<Project> projectsByPublisherId) {
        this.projectsByPublisherId = projectsByPublisherId;
    }
}
