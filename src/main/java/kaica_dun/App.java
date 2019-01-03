package kaica_dun;

import kaica_dun.resources.TestDb;
import kaica_dun_system.MenuMain;
import kaica_dun_system.User;
import kaica_dun_system.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

import static java.lang.System.out;


//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@EntityScan({"kaica_dun.entities", "kaica_dun_system"})
@Import({TestDb.class})//, DataSourceCfg.class, EntityManagerFactoriesCfg.class, TransactionManagersCfg.class})

// This:
//@SpringBootApplication
// is equivalent to this:
@Configuration
@EnableAutoConfiguration
@ComponentScan({"kaica_dun_system", "kaica_dun"})

public class App implements CommandLineRunner {
    // This logger has a name so that it can retrieved for use from anywhere in the application.
    private static final Logger log = LogManager.getLogger("MAIN");

    @Autowired
    private UserServiceImpl usi;

    @Autowired
    private TestDb testdb;

    @Autowired
    private MenuMain menuMain;

    @Autowired
    @Qualifier("HibernateSessionFactory")
    SessionFactory sessionFactory;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }


    public void run(String... strings) {

        try {
            out.println("\n- - - K A I C A    D U N G E O N - - - ");


            log.info("Current objects in DB: {}", usi.findAll());

            Long userId = usi.createUser(new User("user1", "123"));
            log.info("Person created in DB : {}", userId);

            Long userId2 = usi.createUser(new User("user2", "123"));
            log.info("Person created in DB : {}", userId2);

            usi.printUserList();

            menuMain.display();

            testdb.main();



        } catch (Exception e) {
            log.warn(e);
            e.printStackTrace();
        }

        sessionFactory.close();
        System.exit(0);
    }


// Trying to load full Bean list from application context
/*    List<Object> beanList = getInstantiatedSigletons(this.applicationContext);
        log.debug(beanList);

    public static List<Object> getInstantiatedSigletons(ApplicationContext ctx) {
        List<Object> singletons = new ArrayList<Object>();

        String[] all = ctx.getBeanDefinitionNames();

        ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext) ctx).getBeanFactory();
        for (String name : all) {
            Object s = clbf.getSingleton(name);
            if (s != null)
                singletons.add(s);
        }

        return singletons;

    }*/


}



