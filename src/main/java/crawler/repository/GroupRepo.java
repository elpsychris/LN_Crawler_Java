package crawler.repository;

import crawler.model.GroupEntity;

public class GroupRepo extends AbstractRepo<GroupEntity> {
    public GroupRepo() {
        this.idKey = "groupId";
    }

    public GroupEntity findExist(GroupEntity obj) {
        try {
            return this.findExist(GroupEntity.class, obj.getGroupId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
