package crawler.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class People {
    private int peopleId;
    private String name;
    private String othername;
    private Timestamp dob;
    private Integer originId;
    private String contact;

    @Id
    @Column(name = "people_id")
    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "othername")
    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    @Basic
    @Column(name = "dob")
    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
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
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People people = (People) o;

        if (peopleId != people.peopleId) return false;
        if (name != null ? !name.equals(people.name) : people.name != null) return false;
        if (othername != null ? !othername.equals(people.othername) : people.othername != null) return false;
        if (dob != null ? !dob.equals(people.dob) : people.dob != null) return false;
        if (originId != null ? !originId.equals(people.originId) : people.originId != null) return false;
        if (contact != null ? !contact.equals(people.contact) : people.contact != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = peopleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (othername != null ? othername.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (originId != null ? originId.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }
}
