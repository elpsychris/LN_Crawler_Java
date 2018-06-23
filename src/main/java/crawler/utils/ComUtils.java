package crawler.utils;

import javassist.bytecode.ByteArray;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComUtils {
    private final static String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private final static int RETRY_INTERVAL = 20000;
    private final static int TIMEOUT = 6000;
    private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private final static String HASH_ALGO = "MD5";
    private final static SimpleDateFormat timeFormatter = new SimpleDateFormat("ddMMyyyy:hhmmssSSS");
    private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
    private final static Logger logger = Logger.getLogger();


    public static String getCurrentTimeString() {
        return timeFormatter.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateString() {
        return dateFormatter.format(Calendar.getInstance().getTime());
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
            } catch (IOException e) {
                logger.log(Logger.LOG_LEVEL.INFO, "Cannot connect to " + uri, e);
                int waitTime = RETRY_INTERVAL;
                // check retry-after Header
                Map<String, List<String>> headers = connection.getHeaderFields();
                List<String> retryAfter = headers.get("retry-after");
                if (retryAfter != null) {
                    waitTime = Integer.parseInt(retryAfter.get(0));
                    logger.info("Found Retry-After Header in Response Object");
                }

                // check rate
                StringBuilder content = new StringBuilder();
                // check rate
                content.append("\n=== INFORMATION ===");
                for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                    if (header.getKey() == null) {
                        continue;
                    }
                    if (header.getKey().toLowerCase().contains("limit")) {
                        content.append("\n* " + header.getKey());
                        for (String val : header.getValue()) {
                            content.append("\n\t |" + val);
                        }
                    }
                }
                logger.info("Retry after: " + waitTime + "s");
                logger.info(content.toString());

                if (waitTime > 200) {
                    waitTime = RETRY_INTERVAL;
                }

                connection = openConnection(url);

                Thread.sleep(waitTime * 1000);
                cont = true;
            }
        }

        return httpResult;
    }

    private static URLConnection openConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT_HEADER);

        return connection;
    }

    public static StringBuffer cleanHTML(InputStream httpResult) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(httpResult, "UTF-8");

        Pattern pattern = null;
        Matcher matcher = null;

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        stringBuffer.append(XML_DECLARATION + "\n");
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("<html") && line.contains("xmlns=\"http://www.w3.org/1999/xhtml\"")) {
                line = line.replace("<html", "<html xmlns=\"http://www.w3.org/1999/xhtml\"");
            }
            if (line.contains("src") || line.contains("href")) {
                line = line.replace("&", "&amp;");
            }

            // Remove all meta tag
            line = line.replaceAll("<meta[ ]?[^>]+[ ]?>", "");

            // Remove all base tag
            line = line.replaceAll("<base[ ]?[^>]+[ ]?>", "");

            // Remove all link tag
            line = line.replaceAll("<link ]?[^>]+[ ]?>", "");

            // Remove all input tag
            line = line.replaceAll("<input[ ]?[^>]+[ ]?>", "");

            // Remove all br tag
            line = line.replaceAll("<[/]?br[/]?>", "");

            // Remove all li alone tag
            line = line.replaceAll("<li class=\"filter-type_item\">", "");
//            line = line.replaceAll("<li class=\"filter-type_item\">", "");

            line = line.replace("<main class=\"sect-body none force-block-l clear long-text\" style=\"word-wrap: break-word;\"", "<main class=\"sect-body none force-block-l clear long-text\" style=\"word-wrap: break-word;\">");

            // (R) char and ... and non-breaking space char replacement
            line = line
                    .replace("&reg;", "&#174;")
                    .replace("&hellip;", "")
                    .replace("&nbsp;", "");

            pattern = Pattern.compile("<img [^>]+[^/]>");
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                String sMatched = matcher.group(0);
                int start = line.indexOf(sMatched);
                line = line.substring(0, start + sMatched.length()) + "</img>" + line.substring(start + sMatched.length());
            }

            // remove <h3 class="ln-comment-count">
            line = line.trim();
            if (!line.isEmpty()) {
                if (!line.contains("<h3 class=\"ln-comment-count\">") && !line.contains("<hr class=\"ln-comment\">")) {
                    stringBuffer.append(line.trim() + "\n");

                }
            }


        }

        String document = stringBuffer.toString();
        document = document.replaceAll("<script[^>]*>[^<]*</script>", "");
        stringBuffer = new StringBuffer(document);

        return stringBuffer;
    }

    public static String hashString(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGO);
            messageDigest.update(src.getBytes());
            return new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToFile(File file, String msg) {
        FileOutputStream fos = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fos = new FileOutputStream(file, true);
            streamWriter = new OutputStreamWriter(fos);
            bufferedWriter = new BufferedWriter(streamWriter);

            bufferedWriter.write(msg);
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            logger.log(Logger.LOG_LEVEL.INFO, "File " + file + " not found", e);
        } catch (IOException e) {
            logger.log(Logger.LOG_LEVEL.INFO, "BufferWriter unable to write", e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    logger.info("Cannot close BufferWriter");
                }
            }

            if (streamWriter != null) {
                try {
                    streamWriter.close();
                } catch (IOException e) {
                    logger.info("Cannot close StreamWriter");
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.info("Cannot close FileOutputStream");
                }
            }
        }
    }


}
