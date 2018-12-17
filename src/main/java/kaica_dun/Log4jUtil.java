package kaica_dun;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * https://stackoverflow.com/questions/7624895/how-to-use-log4j-with-multiple-classes
 */
public class Log4jUtil {

    private static Logger logger = Logger.getLogger(Log4jUtil.class);


    public static Logger getMainLogger() {
        // If properties file is in classpath then this is sufficient for configuring logger.

        PropertyConfigurator.configure("log4j.properties");
        logger.debug("Main logger is initialised");
        //logger.info("this is a information log message");
        //logger.warn("this is a warning log message");

        return logger;
    }

    public static Logger getLogger(Class klass) {

        return Logger.getLogger(klass.getClass());
    }

}