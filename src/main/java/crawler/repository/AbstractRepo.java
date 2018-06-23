package crawler.repository;


import crawler.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public abstract class AbstractRepo<T> implements IRepo<T> {
    SessionFactory sessionFactory;

    public AbstractRepo() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public void add(T item) {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
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

