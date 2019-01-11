package kaica_dun.resources;

import kaica_dun_system.menus.MenuMain;
import kaica_dun_system.User;
import kaica_dun_system.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * A class used for testing development issues.
 *
 * Primarily concerning persistence issues but can be used for logical
 * operation tests too.
 */
@Component
public class TestDb {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private MenuMain menuMain;

    /**
     * This method is static and can be called directly.
     */
    public void main() {
        Long newUserId = createUserTest(1);
        printUserListTest();
        User userById = findUserByIdTest(newUserId);
        User userByName = findUserByNameTest("kai");
        //UserLoginTest();

        //MonsterCreatorTest();
        //MonsterCreatorTest2();

        //MonsterFinderTest();

        //DungeonCreatorTest(userById);

        //AvatarEqItemTest(newUser);
        log.info("\n\n------> All tests completed.");
        System.exit(0);  // Quit the application.
    }


    /**
     * Create a new user.
     * Can be default user, kai or carl.
     * Always returns a user object but could be not saved.
     *
     * @param userSelection int defining which defaults to make user from
     * @return user a User instance
     */
    public Long createUserTest(int userSelection) {
        log.info("\n\n------> Persisting new User test...");
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

        Long newUserId = this.service.createUser(user);

        if (newUserId != null) {
            log.info("User '{}' with password '{}' created.", user.getName(), user.getPassword());
        }

        return newUserId;
    }

    public User findUserByIdTest(Long userId) {
        log.info("\n\n------> Finding user by id test...");

        return service.findUserById(1L);
    }

    public void printUserListTest() {
        log.info("\n\n------> Output user list test...");
        service.printUserList();

    }

    public User findUserByNameTest(String userName) {
        log.info("\n\n------> Finding user by name...");
        User user = service.findUserByName(userName);
        return user;
    }
}
/*

    *//**
     * Testing creation of monsters..!
     *//*
    @SuppressWarnings("unchecked")
    public  void MonsterCreatorTest() {
        log.info("\n------> MONSTER_CREATION_TEST:");


        try {

            for (int i = 0; i < 4; i++) {
                Monster monster = MonsterFactory.makeOrc();
                mdao.save(monster);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }


    *//**
     * Testing creation of monsters..2!
     *//*
    @SuppressWarnings("unchecked")
    public void MonsterCreatorTest2() {
        log.info("\n------> MONSTER_CREATION_TEST:");


        // Make 5 monsters - get ready to run!!
        for (int i = 0; i < 4; i++) {
            Monster monster = MonsterFactory.makeOrc();
            mdao.save(monster);
        }

        Util.sleeper(1200); // Artificial delay
    }


    *//**
     *  A test for searching for a monster using the DAO system.
     *//*
    public void MonsterFinderTest() {
        log.info("\n------> MONSTER_FINDER_TEST:");
        Long monsterID = 1L;   // L is marks it as long

        /// Searching with only Hibernate
*//*        log.debug("Using Hibernate no DAO to search for an entity by ID: " + monsterID);
        session = SESSIONUTIL.getSession();
        Monster monster = session.get(Monster.class, monsterID);

        if (monster != null) { // Have to test if this works, am I forgetting something.
            log.debug("Monster found: " + monster.toString());
        }*//*


        /// Searching with the DAO processes
        log.debug("Using DAO to search for an monster by ID: " + monsterID);
        Monster monster = null;

        try {
            Optional<Monster> dbMonster = mdao.findUserById(monsterID);

            if (dbMonster.isPresent()) {
                monster = dbMonster.get();
            }
            log.debug("Found a monster by ID: " + monster.toString());

        } catch (NullPointerException e) {
            log.debug("No monster found with that ID: " + e);
        }
    }


    *//**
     * Testing creation of static Dungeon
     *//*
    public void DungeonCreatorTest(User newUser) {
        log.info("\n------> DUNGEON_TEST:");
        //MainDao mdao = new MainDao(Dungeon.class);

        // Make static dungeon
        log.info("making Dungeon...");
        Util.sleeper(800);
        StaticDungeonFactory msd = new StaticDungeonFactory(newUser);
        log.info("User who made StaticDungeonFactory: '{}'", msd.getUser().getName());
        Util.sleeper(800);

        Dungeon d = msd.buildDungeon();
        log.info("User that owns Dungeon: {}", d.getUser().getName() );

        Util.sleeper(800);
        mdao.save(d);

        Util.sleeper(800); // Artificial sleep.
    }


    *//**
     * Testing creation of Avatar with weapon
     *//*
    public static void AvatarEqItemTest(User newUser) {
        log.info("\n------> PlayerAvatar and Item test:");
        log.info("\nTest disabled.");
 *//*       //Make Item (weapon PH, needs more inheritance)
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
        Util.sleeper(800); // Artificial sleep.*/
