package crawler.repository;

import crawler.model.GenreEntity;
import crawler.model.GroupEntity;

public class GenreRepo extends AbstractRepo<GenreEntity> {
    public GenreRepo() {
        this.idKey = "genreId";
    }

    public GenreEntity findExist(GenreEntity obj) {
        try {
            return this.findExist(GenreEntity.class, obj.getGenreId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
