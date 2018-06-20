package crawler.model;

import javax.persistence.*;

@Entity
@Table(name = "GenreMap", schema = "dbo", catalog = "NU_DB")
public class GenreMapEntity {
    private int id;
    private int projectId;
    private int genreId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @Column
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
