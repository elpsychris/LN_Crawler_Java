package crawler.utils;

import crawler.model.GroupEntity;
import crawler.repository.GroupRepo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class GroupAdapter extends XmlAdapter<GroupEntity, GroupEntity> {
    @Override
    public GroupEntity unmarshal(GroupEntity v) throws Exception {
        GroupRepo groupRepo = new GroupRepo();
        GroupEntity obj = groupRepo.findExist(v);
        if (obj == null) {
            groupRepo.add(v);
            return v;
        } else {
            return obj;
        }
    }

    @Override
    public GroupEntity marshal(GroupEntity v) throws Exception {
        return v;
    }
}
