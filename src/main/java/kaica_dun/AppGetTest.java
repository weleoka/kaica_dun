package kaica_dun;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.RoomType;
import kaica_dun.resources.TestDb;
import kaica_dun.util.MenuException;
import kaica_dun.util.QuitException;
import kaica_dun_system.*;
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

import java.util.List;

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


// lazyInit means the bans are loaded as used. Gained 1 second boot time. todo: learn the @Lazy annotation.
@ComponentScan(basePackages = {"kaica_dun_system", "kaica_dun"}, lazyInit=true)

public class AppGetTest implements CommandLineRunner {
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
    private MovementServiceImpl msi;

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

            log.info("Current users in DB: {}", usi.findAll());


            // Game Creation for testing
            // The Game service needs a user and a selected avatar.
            User firstUser = usi.findUserById(1L);
            usi.setAuthenticatedUser(firstUser);
            List<Avatar> tmp = gsi.fetchAvatarByUser(firstUser);
            Avatar avatar = tmp.get(0);
            gsi.setAvatar(avatar);
            usi.setCurrAvatar(avatar);


            mig.display(true, true, avatar); // Jump straight in the game.

            //testdb.main();  // testing
            //Avatar avatar = avatarInterface.save(new Avatar("Rolphius", "A wiry old warrior.", createdUser));
            //log.info("Avatar created in DB : {}", avatar.getName());
            //log.info("Avatars belonging to user: '{}' are: {}", createdUser.getName(), gsi.fetchAvatarByUser(createdUser));
            //usi.printUserList();

            //Avatar avatar = ai.findById(1L);

            //displayMenu();  // Usual app behaviour

        } catch (MenuException e) {
            log.debug("This is a good bye message from debug mode.");
            log.debug("Usually the application will only quit after a KaicaException.QuitException");
            quit(); // Close session and drop db tables.

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
    private void displayMenu(Avatar avatar) {

        while(true) {

            try {
                menuMain.display(avatar);

            } catch (QuitException e) {
                log.debug(e);
                System.out.println("Thanks for playing Kaica Dungeon!");

                break;
            }
        }
        quit();  // Close session and drop db tables.
    }
}



