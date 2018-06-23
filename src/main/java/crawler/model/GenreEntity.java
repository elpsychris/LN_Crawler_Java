package crawler.model;

import crawler.repository.ProjectRepo;
import crawler.utils.LowerCaseAdapter;
import jdk.internal.instrumentation.TypeMapping;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenreEntity", propOrder = {
        "genreId",
        "genreName",
        "projects"
})
@Table(name = "Genre", schema = "dbo", catalog = "NU_DB")
public class GenreEntity {
    @XmlJavaTypeAdapter(LowerCaseAdapter.class)
    @XmlElement(name = "genre-id")
    private String genreId;
    private String genreName;

    private Set<ProjectEntity> projects = new HashSet<>();

    @Id
    @Column(name = "genre_id")
    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    @Column(name = "description")
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @ManyToMany(mappedBy = "genres", targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
