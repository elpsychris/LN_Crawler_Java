package crawler.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Group {
    private int groupId;
    private String groupName;
    private String groupContact;
    private Collection<Update> updatesByGroupId;

    @Id
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_contact")
    public String getGroupContact() {
        return groupContact;
    }

    public void setGroupContact(String groupContact) {
        this.groupContact = groupContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupId != group.groupId) return false;
        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null) return false;
        if (groupContact != null ? !groupContact.equals(group.groupContact) : group.groupContact != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (groupContact != null ? groupContact.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "groupByUpdateGroup")
    public Collection<Update> getUpdatesByGroupId() {
        return updatesByGroupId;
    }

    public void setUpdatesByGroupId(Collection<Update> updatesByGroupId) {
        this.updatesByGroupId = updatesByGroupId;
    }
}
