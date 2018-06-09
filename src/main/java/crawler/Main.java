package crawler;

import crawler.model.Project;
import crawler.spider.ProjectSpider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate.cfg.xml");
//
//        // creating session factory
//        SessionFactory factory = configuration.buildSessionFactory();
//
//        // creating session object
//        Session session = factory.openSession();

        // creating transaction object
//        Project project = session.get(Project.class, 3);
        ProjectSpider spider = new ProjectSpider();
        spider.setStartUrls("https://ln.hako.re/danh-sach?page=1");
    }
}
