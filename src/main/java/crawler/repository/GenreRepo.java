package crawler.repository;

import crawler.model.GenreEntity;
import crawler.model.GroupEntity;
import crawler.utils.Logger;

public class GenreRepo extends AbstractRepo<GenreEntity> {
    public GenreRepo() {
        this.idKey = "genreId";
    }

    public GenreEntity findExist(GenreEntity obj) {
        try {
            return this.findExist(GenreEntity.class, obj.getGenreId());
        } catch (IllegalAccessException e) {
            logger.log(Logger.LOG_LEVEL.ERROR, e);
        } catch (InstantiationException e) {
            logger.log(Logger.LOG_LEVEL.ERROR, "Error in the findExist function", e);
        }
        return null;
    }
}
