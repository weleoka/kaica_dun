package kaica_dun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class TransactionManagersCfg {

    @Autowired
    EntityManagerFactory emf;

/*    @Autowired
    @Qualifier("entityManagerFactorySecondary")
    EntityManagerFactory emfSecondary;*/

    @Bean(name = "transactionManager")
    @Primary
    public JpaTransactionManager transactionManager()
    {
        return new JpaTransactionManager(emf);
    }

/*    @Bean(name = "transactionManagerSecondary")
    public JpaTransactionManager transactionManagerSecondary()
    {
        return new JpaTransactionManager(emfSecondary);
    }*/
}





/*    @Autowired
    private DataSourceCfg dataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        tm.setDataSource(dataSource);
        return tm;
    }*/
