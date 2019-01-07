package kaica_dun;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.RoomType;
import kaica_dun.resources.TestDb;
import kaica_dun.util.QuitException;
import kaica_dun_system.*;
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
import org.springframework.context.annotation.Profile;

import static java.lang.System.out;


//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@EntityScan({"kaica_dun.entities", "kaica_dun_system"})
@Import({TestDb.class})//, DataSourceCfg.class, EntityManagerFactoriesCfg.class, TransactionManagersCfg.class})

// This:
//@SpringBootApplication

// is equivalent to this:
@Configuration
@Profile("default")
//@Profile("production")
@EnableAutoConfiguration


// lazyInit means the bans are loaded as used. Gained 1 second boot time. todo: learn the @Lazy annotation.
@ComponentScan(basePackages = {"kaica_dun_system", "kaica_dun"}, lazyInit=true)

public class App implements CommandLineRunner {
    // This logger has a name so that it can retrieved for use from anywhere in the application.
    private static final Logger log = LogManager.getLogger("MAIN");

    @Autowired
    private UserServiceImpl usi;

    @Autowired
    private GameServiceImpl gsi;

    @Autowired
    private TestDb testdb;

    @Autowired
    private MenuMain menuMain;

    @Autowired
    ActionEngineServiceImpl aesi;

    @Autowired
    AvatarInterface ai;

    @Autowired
    MenuInGame mig;

    @Autowired
    @Qualifier("HibernateSessionFactory")
    SessionFactory sessionFactory;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    public void run(String... strings) {


        try {
            out.printf(UiString.logo);

            StringBuilder str = new StringBuilder();
            for (RoomType type : RoomType.values()) {
                str.append(String.format("'%s', ", type.name()));
            }
            log.debug("Check of valid room types: {}.", str.toString());

            log.info("Current users in DB: {}", usi.findAll());

            Long userId = usi.createUser(new User("user1", "123"));
            log.info("Person created in DB : {}", userId);

            Long userId2 = usi.createUser(new User("user2", "123"));
            log.info("Person created in DB : {}", userId2);


            // Game Creation for testing
            User createdUser = usi.findUserById(userId);
            Avatar avatar = gsi.createStaticAvatar(createdUser);
            Dungeon dungeon = gsi.makeNewDungeon(createdUser);
            aesi.prime(avatar, dungeon); // testing
            mig.display(true); // testing

            //testdb.main();  // testing
            //Avatar avatar = avatarInterface.save(new Avatar("Rolphius", "A wiry old warrior.", createdUser));
            //log.info("Avatar created in DB : {}", avatar.getName());
            //log.info("Avatars belonging to user: '{}' are: {}", createdUser.getName(), gsi.fetchAvatarByUser(createdUser));
            //usi.printUserList();

            //Avatar avatar = ai.findById(1L);

            displayMenu();  // Usual app behaviour



        } catch (Exception e) {
            log.warn(e);
            e.printStackTrace();
        }

    }

    private void quit() {
        out.println(UiString.goodbyeString);
        sessionFactory.close(); // This is important for Hibernate to drop tables if create-drop is set.
        System.exit(0);
    }

    /**
     * The main menu loop that only stops if a QuitException is thrown.
     */
    private void displayMenu() {

        while(true) {

            try {
                menuMain.display();

            } catch (QuitException e) {
                log.debug(e);
                e.printStackTrace();

                break;
            }
        }
        quit();
    }

// Trying to load full Bean list from application context
// Better to do this by reading log output as security measures prevent access to context.
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



