package loggerUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LogUtils {

    private static final Logger log = LogManager.getLogger(Logger.class);

    public static void info(String message) {
        log.info(message);
       
    }

    public static void warn(String message) {
        log.warn(message);
      
    }

    public static void error(String message) {
        log.error(message);
       
    }

    public static void errorThrowable(Throwable throwable) {
        log.error("Exception: ", throwable);
       
    }

    public static void debug(String message) {
        log.debug(message);
      
    }
}
