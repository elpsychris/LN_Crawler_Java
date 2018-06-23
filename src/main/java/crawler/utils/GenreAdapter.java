package crawler.utils;

import crawler.model.GenreEntity;
import crawler.model.GroupEntity;
import crawler.repository.GenreRepo;
import crawler.repository.GroupRepo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class GenreAdapter extends XmlAdapter<GenreEntity, GenreEntity> {
    @Override
    public GenreEntity unmarshal(GenreEntity v) throws Exception {
        GenreRepo genreRepo = new GenreRepo();
        GenreEntity obj = genreRepo.findExist(v);
        if (obj == null) {
            genreRepo.add(v);
            return v;
        } else {
            return obj;
        }
    }

    @Override
    public GenreEntity marshal(GenreEntity v) throws Exception {
        return v;
    }
}
