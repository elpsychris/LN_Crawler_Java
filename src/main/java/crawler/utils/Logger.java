package crawler.utils;

import java.io.File;
import java.io.IOException;

public class Logger {
    private static Logger logger = null;
    private final static String LOG_DIR = "log";
    private File file = null;

    public enum LOG_LEVEL {
        INFO(1, "INFO"), WARNING(2, "WARN"), ERROR(3, "ERROR"), FATAL(4, "FATAL");

        private int severity = 0;
        private String logType = "";
        private int SILENCE_LEVEL = 1;

        LOG_LEVEL(int severity, String logType) {
            this.severity = severity;
            this.logType = logType;
        }

        public int severity() {
            return severity;
        }

        public String logType() {
            return logType;
        }

        public boolean isSilence() {
            return severity <= SILENCE_LEVEL;
        }
    }

    private Logger(String filename) {
        file = new File(filename);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            info("Cannot create new log directory");
        }
    }

    public static Logger getLogger() {
        if (logger == null) {
            String date = ComUtils.getCurrentDateString();
            String logName = new StringBuilder(LOG_DIR + "/log_").append(date).toString();
            logger = new Logger(logName);
        }
        return logger;
    }

    public void log(LOG_LEVEL level, String message, Exception e) {
        StringBuilder log = new StringBuilder();

        log.append(String.format("[%s | %s]: ", level.logType, ComUtils.getCurrentTimeString()));
        if (message == null) {
            message = "n/a";
        }
        log.append(message);
        if (e != null) {
            log.append("\n\tDETAIL:\t");
            log.append(e.getMessage().replaceAll("\n","\n\t"));
        }

        if (level.isSilence()) {
            System.out.println(log.toString());
        } else {
            System.out.println(log.toString());
            ComUtils.writeToFile(file, log.toString());
        }
    }

    public void log(LOG_LEVEL level, Exception e) {
        log(level, null, e);
    }

    public void info(String msg) {
        log(LOG_LEVEL.INFO, msg, null);
    }
}
