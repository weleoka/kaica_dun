package kaica_dun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class EntityManagerFactoriesCfg {

    @Autowired
    @Qualifier("dataSourcePrimary")
    private DataSource dataSource;

/*
    @Autowired
    @Qualifier("dataSourceSecondary")
    private DataSource dataSourceSecondary;
*/


    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(new String[] {"kaica_dun", "kaica_dun_system"});
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

/*    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean emfSecondary() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSourceSecondary);
        emf.setPackagesToScan(new String[] {"kaica_dun", "kaica_dun_system"});
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }*/
}