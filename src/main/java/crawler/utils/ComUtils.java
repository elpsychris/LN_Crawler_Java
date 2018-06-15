package crawler.utils;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

public class ComUtils {
    private final static String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private final static int RETRY_INTERVAL = 10000;
    private final static int TIMEOUT = 6000;


    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static InputStream getHttp(String uri) throws IOException, InterruptedException {
        URL url = new URL(uri);
        InputStream httpResult = null;
        URLConnection connection = openConnection(url);

        boolean cont = true;
        while (cont) {
            try {
                httpResult = connection.getInputStream();
                cont = false;
                Thread.sleep(1000);
            } catch (IOException e) {
                System.out.println("Failed to connect to " + url);
                System.out.println(e.getMessage());

                openConnection(url);

                Thread.sleep(RETRY_INTERVAL);
                cont = true;

            }
        }

        return httpResult;
    }

    private static URLConnection openConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT_HEADER);
        connection.setReadTimeout(1000);

        return connection;
    }


}
