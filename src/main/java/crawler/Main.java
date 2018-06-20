package crawler;

import crawler.model.ProjectEntity;
import crawler.repository.ProjectRepo;
import crawler.spider.Spider;
import crawler.utils.HibernateUtils;
import crawler.utils.MyURIResolver;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.*;
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

//        ProjectRepo projectRepo = new ProjectRepo();
//
//        ProjectSpider spider = new ProjectSpider(projectRepo);
//        String regex = "<main class=\"sect-body listext-table widthfluid clear\">[\\S\\s]*</article>\\n</main>";
//        Pattern pattern = Pattern.compile(regex);
//
//
//        spider.setStartUrls("https://ln.hako.re/danh-sach?page=1", pattern);

//        File inputFile = new File("src/main/java/crawl_temp/test.xml");
//        File file = new File("src/main/java/crawl_temp/output1.xml");
//        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
//            StringBuffer stringBuffer = MyURIResolver.cleanHTML(fileInputStream);
//            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
//            bf.write(stringBuffer.toString());
//            bf.flush();
//            bf.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Spider spider = new Spider();
        try {
            spider.start("src/main/java/crawler/xmlConfigs/hako_project.xml", "src/main/java/crawler/stylesheet/hako_style.xsl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
