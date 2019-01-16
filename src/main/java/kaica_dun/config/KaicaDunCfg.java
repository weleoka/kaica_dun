package kaica_dun.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class KaicaDunCfg {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private Environment env;

    public boolean debug = false;


    /**
     * Reads the properties and returns a Properties object.
     *
     * Also sets values to fields for access direct or by accessor methods perhaps.
     *
     * @return
     */
    public Properties readProperties() {
        log.debug("Loading kaica_dun configuration file...");
        Properties properties = new Properties();
        properties.put("debug", env.getRequiredProperty("kaica.debug"));

        try {
            this.debug = Boolean.parseBoolean(properties.get("debug").toString());

            if (debug) {
                log.debug("DEBUG mode configuration enabled.");
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return properties;
    }


    public boolean getDebug() {
        return debug;
    }
}