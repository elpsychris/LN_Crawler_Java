package crawler.model;

import javax.persistence.*;

@Entity
@Table(name = "Origin", schema = "dbo", catalog = "NU_DB")
public class OriginEntity {
    private int originId;
    private String countryName;

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

        OriginEntity that = (OriginEntity) o;

        if (originId != that.originId) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originId;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }
}
