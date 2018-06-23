package crawler.repository;

import crawler.model.ProjectEntity;
import crawler.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ProjectRepo extends AbstractRepo<ProjectEntity> {
    public ProjectRepo() {
        this.idKey = "projectId";
    }

    public boolean checkExist(ProjectEntity project) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ProjectEntity.class);
        criteria.add(Restrictions.like("projectHash",project.getProjectHash()));
        return !criteria.list().isEmpty();
    }

}
