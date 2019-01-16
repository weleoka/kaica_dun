package kaica_dun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DataSourceCfg {

    @Autowired
    private Environment env;

    @Bean(name="dataSourcePrimary")
    @Primary
    @ConfigurationProperties(prefix = "datasource.sqldb")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

/*    @Bean(name="dataSourceSecondary")
    @ConfigurationProperties(prefix = "datasource.hsqldb")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }*/
}






/*        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("datasource.driver"));
        dataSource.setUrl(env.getRequiredProperty("datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("datasource.password"));
        return dataSource;*/
