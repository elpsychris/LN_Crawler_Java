package crawler.model;

import javax.persistence.*;

@Entity
@Table(name = "Group", schema = "dbo", catalog = "NU_DB")
public class GroupEntity {
    private int groupId;
    private String groupName;
    private String groupContact;

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

        GroupEntity that = (GroupEntity) o;

        if (groupId != that.groupId) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (groupContact != null ? !groupContact.equals(that.groupContact) : that.groupContact != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (groupContact != null ? groupContact.hashCode() : 0);
        return result;
    }
}
