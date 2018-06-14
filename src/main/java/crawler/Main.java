package crawler;

import crawler.model.ProjectEntity;
import crawler.repository.ProjectRepo;
import crawler.spider.ProjectSpider;
import crawler.utils.HibernateUtils;

import java.util.regex.Pattern;

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
        HibernateUtils.initSessionFactory();
        ProjectEntity project = new ProjectEntity();
        project.setProjectAlterName("kakakakkaka");
        project.setProjectHash("adafdsd");
        project.setProjectName("asas");
        project.setProjectOriginId(1);

        ProjectRepo projectRepo = new ProjectRepo();

        ProjectSpider spider = new ProjectSpider(projectRepo);
        String regex = "<main class=\"sect-body listext-table widthfluid clear\">[\\S\\s]*</article>\\n</main>";
        Pattern pattern = Pattern.compile(regex);


        spider.setStartUrls("https://ln.hako.re/danh-sach?page=1", pattern);


    }
}
