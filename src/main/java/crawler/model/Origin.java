package crawler.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Origin {
    private int originId;
    private String countryName;
    private Collection<Project> projectsByOriginId;

    @Id
    @Column(name = "origin_id")
    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    @Basic
    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Origin origin = (Origin) o;

        if (originId != origin.originId) return false;
        if (countryName != null ? !countryName.equals(origin.countryName) : origin.countryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originId;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "originByProjectOriginId")
    public Collection<Project> getProjectsByOriginId() {
        return projectsByOriginId;
    }

    public void setProjectsByOriginId(Collection<Project> projectsByOriginId) {
        this.projectsByOriginId = projectsByOriginId;
    }
}
