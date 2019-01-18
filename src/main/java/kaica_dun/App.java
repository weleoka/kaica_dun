package kaica_dun;

import kaica_dun.config.KaicaDunCfg;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.RoomType;
import kaica_dun.resources.TestDb;
import kaica_dun.util.MenuException;
import kaica_dun.util.QuitException;
import kaica_dun_system.GameServiceImpl;
import kaica_dun_system.UiString;
import kaica_dun_system.User;
import kaica_dun_system.UserServiceImpl;
import kaica_dun_system.menus.MenuInGame;
import kaica_dun_system.menus.MenuMain;
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

//import org.hsqldb.jdbcDriver;
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


// lazyInit means the beans are loaded as used. Gained 1 second boot time.
@ComponentScan(basePackages = {"kaica_dun_system", "kaica_dun"}, lazyInit=true)
public class App implements CommandLineRunner {
    // This logger has a name so that it can retrieved for use from anywhere in the application.
    private static final Logger log = LogManager.getLogger("MAIN");

    @Autowired
    private KaicaDunCfg kcfg;

    @Autowired
    private UserServiceImpl usi;

    @Autowired
    private GameServiceImpl gsi;

    @Autowired
    private TestDb testdb;

    @Autowired
    private MenuMain menuMain;

    @Autowired
    MenuInGame mig;

    @Autowired
    @Qualifier("HibernateSessionFactory")
    SessionFactory sessionFactory;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    /**
     * Main entry point for a spring boot application.
     *
     * @param strings
     */
    public void run(String... strings) {

        kcfg.readProperties(); // Read config file.

        if (kcfg.getDebug()) {
            log.debug("DEBUG mode is enabled");

            try {
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
                // The Game service needs a user and a selected avatar.
                User createdUser = usi.findUserById(userId);

                Avatar avatar = gsi.createStaticAvatar(createdUser);

                mig.display(avatar, true); // Jump straight in the game.

                //testdb.main();  // testing
                //Avatar avatar = avatarInterface.save(new Avatar("Rolphius", "A wiry old warrior.", createdUser));
                //log.info("Avatar created in DB : {}", avatar.getName());
                //log.info("Avatars belonging to user: '{}' are: {}", createdUser.getName(), gsi.fetchAvatarByUser(createdUser));
                //usi.printUserList();

                //Avatar avatar = ai.findById(1L);

            } catch (MenuException e) {
                log.debug("This is a good bye message from debug mode.");
                log.debug("Usually the application will only quit after a KaicaException.QuitException");
                quit(); // Close session and drop db tables.

            } catch (Exception e) {
                log.warn(e);
                e.printStackTrace();
                quit();
            }
        } else {
            // Usual app behaviouFORMAT_SQLr
            out.printf(UiString.logo);
            displayMainMenu();
        }

    }


    /**
     * The main menu loop that only stops if a QuitException is thrown.
     */
    private void displayMainMenu() {

        while(true) {

            try {
                menuMain.display();

            } catch (QuitException e) {
                log.debug(e);
                System.out.println("Thanks for playing Kaica Dungeon!");
                break;

            } catch (Exception e) {
                log.warn(e);
                e.printStackTrace();
                break;
            }
        }
        quit();  // Close session.
    }


    /**
     * Closes the application and does clean up if any.
     */
    private void quit() {
        out.println(UiString.goodbyeString);
        sessionFactory.close(); // This is important for Hibernate to drop tables if create-drop is set.
        System.exit(0);
    }
}




