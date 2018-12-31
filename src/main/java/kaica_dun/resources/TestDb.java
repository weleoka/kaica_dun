package kaica_dun.resources;

import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.MainDao;

import kaica_dun.dao.UserDao;
import kaica_dun.entities.*;
import kaica_dun.util.Util;

import kaica_dun_system.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;

import java.util.List;


/**
 * A class used for testing development issues.
 *
 * Primarily concerning persistence issues but can be used for logical
 * operation tests too.
 */
@Component
public class TestDb {

    private static final Logger log = LogManager.getLogger();

    //static MainHDao mdao = daoFactory.getMainHDao();

    /**
     * This method is static and can be called directly.
     *
     * @param args              an array of strings of arguments
     */
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();

        Long newUserId = createUserTest(2);
        printUserListTest();
        User userById = findUserByIdTest(newUserId);
        User userByName = findUserByNameTest("kai");
        //UserLoginTest();

        MonsterCreatorTest();
        //MonsterCreatorTest2();

        MonsterFinderTest();

        //DungeonCreatorTest(newUser);

        //AvatarEqItemTest(newUser);
        System.exit(0);  // Quit the application.
    }


    /**
     * Create a new user.
     * Can be default user, kai or carl.
     * Always returns a user object but could be not saved.
     * @param userSelection int defining which defaults to make user from
     * @return user a User instance
     */
    public static Long createUserTest (int userSelection) {
        log.info("\n------> Persisting new User test...");
        UserServiceImpl us = new UserServiceImpl();
        User user;

        switch (userSelection) {

            case 1:
                user = new User("carl", "pass");
                break;

            case 2:
                user = new User("kai", "123");
                break;

            default:
                user = new User("noname", "nopass");
                break;
        }

        Long newUserId = us.createUser(user);

        if (newUserId != null) {
            log.info("User '{}' with password '{}' created.", user.getName(), user.getPassword());
        }

        return newUserId;
    }

    public static User findUserByIdTest(Long userId) {
        log.info("\n------> Finding user by id test...");
        UserServiceImpl us = new UserServiceImpl();

        return us.findById(1L);
    }

    public static void printUserListTest() {
        log.info("\n------> Output user list test...");
        UserServiceImpl us = new UserServiceImpl();
        List<User> userList = us.findAll();
        System.out.println("- - userlist - -");
        for (int id = 0; id < userList.size(); id++) {

            try {
                User currentUser = userList.get(id);
                System.out.printf("%s - UserName: %s\n", id, currentUser.getName());

            } catch (IndexOutOfBoundsException e) {
                log.warn("Index out of bounds: {}", e);
            }
        }
        System.out.println();
    }

    public static User findUserByNameTest(String userName) {
        log.info("\n------> Finding user by name...");
        UserServiceImpl us = new UserServiceImpl();

        return us.findByName(userName);
    }



    /**
     * Testing creation of monsters..!
     */
    @SuppressWarnings("unchecked")
    public static void MonsterCreatorTest() {
        log.info("\n------> MONSTER_CREATION_TEST:");
        MainDao mdao = new MainDao(Monster.class);

        try {

            for (int i = 0; i < 4; i++) {
                Monster monster = MonsterFactory.makeOrc();
                mdao.create(monster);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }


    /**
     * Testing creation of monsters..2!
     */
    @SuppressWarnings("unchecked")
    public static void MonsterCreatorTest2() {
        log.info("\n------> MONSTER_CREATION_TEST:");

        MainDao mdao = new MainDao(Monster.class);

        // Make 5 monsters - get ready to run!!
        for (int i = 0; i < 4; i++) {
            Monster monster = MonsterFactory.makeOrc();
            mdao.create(monster);
        }

        Util.sleeper(1200); // Artificial delay
    }


    /**
     *  A test for searching for a monster using the DAO system.
     */
    public static void MonsterFinderTest() {
        log.info("\n------> MONSTER_FINDER_TEST:");
        Long monsterID = 1L;   // L is marks it as long

        /// Searching with only Hibernate
/*        log.debug("Using Hibernate no DAO to search for an entity by ID: " + monsterID);
        session = SESSIONUTIL.getSession();
        Monster monster = session.get(Monster.class, monsterID);

        if (monster != null) { // Have to test if this works, am I forgetting something.
            log.debug("Monster found: " + monster.toString());
        }*/


        /// Searching with the DAO processes
        log.debug("Using DAO to search for an monster by ID: " + monsterID);
        MainDao mdao = new MainDao(Monster.class);

        try {
            Monster monsterFoundByDao = (Monster) mdao.read(monsterID);
            log.debug("Found a monster by ID: " + monsterFoundByDao.toString());

        } catch (NullPointerException e) {
            log.debug("No monster found with that ID: " + e);
        }
    }


    /**
     * Testing creation of static Dungeon
     */
    public static void DungeonCreatorTest(User newUser) {
        log.info("\n------> DUNGEON_TEST:");
        MainDao mdao = new MainDao(Dungeon.class);

        // Make static dungeon
        log.info("making Dungeon...");
        Util.sleeper(800);
        makeStaticDungeon msd = new makeStaticDungeon(newUser);
        log.info("User who made makeStaticDungeon: '{}'", msd.getUser().getName());
        Util.sleeper(800);

        Dungeon d = msd.buildDungeon();
        log.info("User that owns Dungeon: {}", d.getUser().getName() );

        Util.sleeper(800);
        mdao.create(d);

        Util.sleeper(800); // Artificial sleep.
    }


    /**
     * Testing creation of Avatar with weapon
     */
    public static void AvatarEqItemTest(User newUser) {
        log.info("\n------> PlayerAvatar and Item test:");

        //Make Item (weapon PH, needs more inheritance)
        Item wep1 = new Item("The Smashanizer","Smashing!", 4, 2,0);
        //Make item that is not to be equipped to PlayerAvatar to check optionality of OneToOne
        Item wep2 = new Item("Rusty Sword", "Nobody wants to equipp a rusty sword...", 0, 1,0);
        //Make an item to be equipped immediately through constructor
        Item wep3 = new Item("Sharp Sword", "Ah, much better!", 3, 5,0);
        //Make an armor to be equipped to Avatar
        Item arm1 = new Item("Studded Leather", "A full suit of studded leather armor.", 0, 0, 3);


        //Make static PlayerAvatar without weapon Equipped
        Avatar pa1 = new Avatar(newUser, "KaiEquipsWeapon", "Run!", "User Avatar", 90, 90, 1, 2);
        //Equip weapon to PlayerAvatar
        pa1.equippWeapon(wep3);

        //Make static PlayerAvatar with weapon Equipped
        Avatar pa2 = new Avatar(newUser, "KaiWithWeaponEquipsArmor", "Oh, yeah!", "User Avatar", 90, 90, 1, 2, wep1);
        pa2.equippArmor(arm1);


        //saving the persistent PlayerAvatar
        MainDao mdao = new MainDao(Avatar.class);
        mdao.create(pa1);
        mdao.create(pa2);
        pa2.takeDamage(13);     //Should set KaiWithWeaponEquipsArmors currHealth to 80(90 - (13 - 3))
        mdao.update(pa2);    //Works!

        mdao.create(wep2);


        //TODO test to unequipp weapon and update database to see if it works as planned
        Util.sleeper(800); // Artificial sleep.
    }


    /**
     * Testing user functionality
     */
    public static void UserLoginTest() {
        log.info("\n------> User login test");
        MenuMain mainMenu = new MenuMain();
        mainMenu.display();
    }
}
