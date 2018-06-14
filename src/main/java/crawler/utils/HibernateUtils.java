package crawler.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {
    private static SessionFactory sessionFactory = buildSessionFactory();

    public static boolean initSessionFactory() {
        Session session = sessionFactory.openSession();

        try {
            session.createSQLQuery("SELECT 1;").list();
            return true;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
//            session.close();
        }

        System.out.println("Error sml!!");
        return false;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
        System.out.println("Connection and Session Factory is closed");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        // creating session factory
        SessionFactory factory = configuration.buildSessionFactory();
        return factory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
