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

    public boolean debug;

    private Properties getProperties() {
        log.debug("Loading kaica_dun configuration...");
        Properties properties = new Properties();
        properties.put(debug, env.getRequiredProperty("kaica.debug"));
        return properties;
    }
}