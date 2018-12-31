package kaica_dun;

import kaica_dun.resources.TestDb;

import kaica_dun_system.BeansJpaCfg;
import kaica_dun_system.HibernateCfg;
import kaica_dun_system.User;
import kaica_dun_system.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static java.lang.System.out;

@SpringBootApplication
public class App implements CommandLineRunner {
    // This logger has a name so that it can retrieved for use from anywhere in the application.
    private static final Logger log = LogManager.getLogger("MAIN");

    @Autowired
    private UserService service;


    public static void main(String[] args) {

        SpringApplication.run(HibernateCfg.class, args);

        //SpringApplication.run(BeansJpaCfg.class, args);








        out.println("- - - K A I C A    D U N G E O N - - - ");


        AnnotationConfigApplicationContext context = null;


        try {
            context = new AnnotationConfigApplicationContext(TestDb.class);
            TestDb testDb = context.getBean(TestDb.class);

            testDb.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.close();
        }

        //User player = new User();
        //makeStaticDungeon msd = new makeStaticDungeon(player);
        //Dungeon d = msd.buildDungeon();


        // Testing scenarios:

        //TestDb.main(arguments); // Run all the tests.
        //TestDb.testDb_Monster();
        //TestDb.testDb_Room();
    }


    @Override
    public void run(String... strings) {

        log.info("Current objects in DB: {}", this.service.findAll());

        Long userId = this.service.createUser(new User("testUSer", "123"));
        log.info("Person created in DB : {}", userId);
    }


}



