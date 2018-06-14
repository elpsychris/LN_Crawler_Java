package crawler.repository;


import crawler.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public abstract class AbstractRepo<T> implements IRepo<T> {
    private Session session;

    public AbstractRepo() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        this.session = sessionFactory.getCurrentSession();
    }

    @Override
    public void add(T item) {
        try {
            session.getTransaction().begin();
            session.save(item);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(T item) {
        try {
            session.getTransaction().begin();
            session.update(item);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void remove(T item) {
        try {
            session.getTransaction().begin();
            session.delete(item);
            session.flush();
            session.getTransaction().rollback();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<T> query(Criteria criteria) {
        return criteria.list();
    }
}

