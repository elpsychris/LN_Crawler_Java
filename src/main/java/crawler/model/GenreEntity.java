package crawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Genre", schema = "dbo", catalog = "NU_DB")
public class GenreEntity {
    private String genreId;
    private String genreName;

    @Column(name = "genre_id")
    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    @Column(name = "genre")
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
