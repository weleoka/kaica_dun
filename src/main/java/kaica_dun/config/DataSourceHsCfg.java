package kaica_dun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DataSourceHsCfg {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    //@Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("datasource.driver2"));
        dataSource.setUrl(env.getRequiredProperty("datasource.url2"));
        dataSource.setUsername(env.getRequiredProperty("datasource.username2"));
        dataSource.setPassword(env.getRequiredProperty("datasource.password2"));
        return dataSource;
    }
}
