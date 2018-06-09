package crawler.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Update {
    private int updateId;
    private String updateName;
    private String updateVol;
    private Timestamp updateDate;
    private Integer updateGroup;
    private String updateLink;
    private Group groupByUpdateGroup;

    @Id
    @Column(name = "update_id")
    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    @Basic
    @Column(name = "update_name")
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Basic
    @Column(name = "update_vol")
    public String getUpdateVol() {
        return updateVol;
    }

    public void setUpdateVol(String updateVol) {
        this.updateVol = updateVol;
    }

    @Basic
    @Column(name = "update_date")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "update_group")
    public Integer getUpdateGroup() {
        return updateGroup;
    }

    public void setUpdateGroup(Integer updateGroup) {
        this.updateGroup = updateGroup;
    }

    @Basic
    @Column(name = "update_link")
    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Update update = (Update) o;

        if (updateId != update.updateId) return false;
        if (updateName != null ? !updateName.equals(update.updateName) : update.updateName != null) return false;
        if (updateVol != null ? !updateVol.equals(update.updateVol) : update.updateVol != null) return false;
        if (updateDate != null ? !updateDate.equals(update.updateDate) : update.updateDate != null) return false;
        if (updateGroup != null ? !updateGroup.equals(update.updateGroup) : update.updateGroup != null) return false;
        if (updateLink != null ? !updateLink.equals(update.updateLink) : update.updateLink != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = updateId;
        result = 31 * result + (updateName != null ? updateName.hashCode() : 0);
        result = 31 * result + (updateVol != null ? updateVol.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (updateGroup != null ? updateGroup.hashCode() : 0);
        result = 31 * result + (updateLink != null ? updateLink.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "update_group", referencedColumnName = "group_id", insertable = false, updatable = false)
    public Group getGroupByUpdateGroup() {
        return groupByUpdateGroup;
    }

    public void setGroupByUpdateGroup(Group groupByUpdateGroup) {
        this.groupByUpdateGroup = groupByUpdateGroup;
    }
}
