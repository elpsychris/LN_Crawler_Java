package crawler.model;

import javax.persistence.*;

@Entity
@Table(name = "Update_Info", schema = "dbo", catalog = "NU_DB")
public class UpdateInfoEntity {
    private int updateInfoId;
    private Integer updateId;
    private Integer projectId;

    @Id
    @Column(name = "update_info_id")
    public int getUpdateInfoId() {
        return updateInfoId;
    }

    public void setUpdateInfoId(int updateInfoId) {
        this.updateInfoId = updateInfoId;
    }

    @Basic
    @Column(name = "update_id")
    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateInfoEntity that = (UpdateInfoEntity) o;

        if (updateInfoId != that.updateInfoId) return false;
        if (updateId != null ? !updateId.equals(that.updateId) : that.updateId != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = updateInfoId;
        result = 31 * result + (updateId != null ? updateId.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        return result;
    }
}
