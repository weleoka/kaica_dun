package kaica_dun.resources;


import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.MonsterDao;

import kaica_dun.dao.UserDao;
import kaica_dun.entities.*;
import kaica_dun.util.Util;

import kaica_dun_system.MenuMain;
import kaica_dun_system.SessionUtil;
import kaica_dun_system.User;
import kaica_dun_system.UserControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;


/**
 * A class used for testing development issues.
 *
 * Primarily concerning persistence issues but can be used for logical
 * operation tests too.
 */
public class TestDb {

    private static final Logger log = LogManager.getLogger();
    private static final SessionUtil SESSIONUTIL = SessionUtil.getInstance();
    private static Session session = null;

    private DaoFactory daoFactory = DaoFactory.instance(DaoFactory.HIBERNATE);



    /**
     * This class is static and can be called directly.
     *
     * @param args              an array of strings of arguments
     */
    public static void main(String[] args) {

        Long newUserId = createUserTest(2);
        //printUserListTest();
        User userById = findUserByIdTest(newUserId);
        User userByName = findUserByNameTest("kai");

        System.exit(0);  // Quit the application.

        UserLoginTest();

        //MonsterCreatorTest();

        //MonsterFinderTest();

        //DungeonCreatorTest(newUser);

        //AvatarEqItemTest(newUser);
    }


    /**
     * Create a new user.
     * Can be default user, kai or carl.
     * Always returns a user object but could be not saved.
     * @param userSelection int defining which defaults to make user from
     * @return user a User instance
     */
    public static Long createUserTest (int userSelection) {
        log.info("------> Persisting new User test...");
        UserControl USERCONTROL = UserControl.getInstance();
        User user;

        switch (userSelection) {

            case 1:
                user = new User("carl", "pass");
                break;

            case 2:
                log.debug("ADSFASDFSAF");
                user = new User("kai", "123");
                break;

            default:
                user = new User("noname", "nopass");
                break;
        }

        Long newUserId = USERCONTROL.create(user);

        if (newUserId != null) {
            log.info("User '{}' with password '{}' created.", user.getName(), user.getPassword());
        }

        return newUserId;
    }

    public static User findUserByIdTest(Long userId) {
        log.info("------> Finding user by id test...");
        UserControl USERCONTROL = UserControl.getInstance();

        return USERCONTROL.findById(1L);
    }

    public static void printUserListTest() {
        log.info("------> Output user list test...");
        UserControl USERCONTROL = UserControl.getInstance();
        List<User> userList = USERCONTROL.findAll();

        for (int id = 0; id < userList.size(); id++) {

            try {
                User currentUser = userList.get(id);
                System.out.printf("\n%s - UserName: %s", id, currentUser.getName());

            } catch (IndexOutOfBoundsException e) {
                log.warn("Index out of bounds: {}", e);
            }
        }
    }

    public static User findUserByNameTest(String userName) {
        log.info("------> Finding user by name...");
        UserControl USERCONTROL = UserControl.getInstance();

        return USERCONTROL.findByName(userName);
    }



    /**
     * Testing creation of monsters..!
     */
    public static void MonsterCreatorTest() {
        session = SESSIONUTIL.getSession();
        log.info("------> MONSTER_CREATION_TEST:");

        // Make 5 monsters - get ready to run!!
        for (int i = 0; i < 4; i++) {
            Monster monster = MonsterFactory.makeOrc();
            session.save(monster);
        }
        session.getTransaction().commit();

        Util.sleeper(1200); // Artificial delay
    }


    /**
     *  A test for searching for a monster using the DAO system.
     */
    public static void MonsterFinderTest() {
        log.info("------> MONSTER_FINDER_TEST:");
        Long monsterID = 1L;   // L is marks it as long

        /// Searching with only Hibernate
        log.debug("Using Hibernate no DAO to search for an entity by ID: " + monsterID);
        session = SESSIONUTIL.getSession();
        Monster monster = session.get(Monster.class, monsterID);

        if (monster != null) { // Have to test if this works, am I forgetting something.
            log.debug("Monster found: " + monster.toString());
        }


        /// Searching with the DAO processes
        log.debug("Using DAO to search for an monster by ID: " + monsterID);
        log.debug("Starting up a DAO Factory for Hibernate infrastructure.");
        DaoFactory daoFactory = DaoFactory.instance(DaoFactory.HIBERNATE); // Hibernate Dao.

        log.debug("Getting a specific DAO (MonsterDao) from Hibernate DAO Factory.");
        MonsterDao monsterDao = daoFactory.getMonsterDao();

        log.debug("Searching for monsterID through DAO: " + monsterID);
        try {
            Monster monsterFoundByDao = monsterDao.findById(monsterID, true);
            log.debug("Found a Room by ID: " + monsterFoundByDao.toString());

        } catch (NullPointerException e) {
            log.debug("No monster found with that ID: " + e);
        }
    }


    /**
     * Testing creation of static Dungeon
     */
    public static void DungeonCreatorTest(User newUser) {
        log.info("------> DUNGEON_TEST:");
        session = SESSIONUTIL.getSession();

        // Make static dungeon
        log.info("making Dungeon...");
        Util.sleeper(800);
        makeStaticDungeon msd = new makeStaticDungeon(newUser);
        log.info("User who made makeStaticDungeon: '{}'", msd.getUser().getName());
        Util.sleeper(800);

        Dungeon d = msd.buildDungeon();
        log.info("User that owns Dungeon: {}", d.getUser().getName() );

        Util.sleeper(800);
        session.save(d);
        session.getTransaction().commit();

        Util.sleeper(800); // Artificial sleep.
    }


    /**
     * Testing creation of Avatar with weapon
     */
    public static void AvatarEqItemTest(User newUser) {
        log.info("------> PlayerAvatar and Item test:");

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
        session = SESSIONUTIL.getSession();
        session.save(pa1);
        session.save(pa2);
        pa2.takeDamage(13);     //Should set KaiWithWeaponEquipsArmors currHealth to 80(90 - (13 - 3))
        session.update(pa2);    //Works!

        session.save(wep2);


        //TODO test to unequipp weapon and update database to see if it works as planned
        session.getTransaction().commit();
        Util.sleeper(800); // Artificial sleep.
    }


    /**
     * Testing user functionality
     */
    public static void UserLoginTest() {
        log.info("------> User login test");
        MenuMain mainMenu = new MenuMain();
        mainMenu.display();
    }
}
















    /* - - - Example 1 - Usage of session and sessionFactory

    public void testBasicUsage() {
        // create a couple of events...
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Event( "Our very first event!", new Date() ) );
        session.save( new Event( "A follow up event", new Date() ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Event" ).list();
        for ( Event event : (List<Event>) result ) {
            System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
        }
        session.getTransaction().commit();
        session.close();
    }



        // - - - Example 2 - Usage of JPA EntityManager
        EntityManager em = null;

        // TM is a class bundled with the example code of Hibernate 5 book.
        UserTransaction tx = TM.getUserTransaction();

        try {
            tx.begin();
            em = JPA.createEntityManager();


            tx.commit();

        } catch (Exception ex) {

            // Transaction rollback, exception handling
            // ...

        } finally {

            if (em != null && em.isOpen())
                em.close(); // You create it, you close it!

        }


        // - - - Example 3 - Seting an item to persist
        Item item = new Item();
        item.setName("Some Item");

        em.persist(item);
        Long ITEM_ID = item.getId();

### session.persist(my_object) Vs. session.save(my_object)
persist() is well defined. It makes a transient instance persistent. However, it doesn't guarantee that the identifier value will be assigned to the persistent instance immediately, the assignment might happen at flush time. The spec doesn't say that, which is the problem I have with persist().
persist() also guarantees that it will not execute an INSERT statement if it is called outside of transaction boundaries. This is useful in long-running conversations with an extended Session/persistence context. A method like persist() is required.
save() does not guarantee the same, it returns an identifier, and if an INSERT has to be executed to get the identifier (e.g. "identity" generator, not "sequence"), this INSERT happens immediately, no matter if you are inside or outside of a transaction. This is not good in a long-running conversation with an extended Session/persistence context.


### Detecting entity state using the identifier
Sometimes you need to know whether an entity instance is transient, persistent, or
detached. An entity instance is in persistent state if EntityManager#contains(e)
returns true. It’s in transient state if PersistenceUnitUtil#getIdentifier(e)
returns null. It’s in detached state if it’s not persistent, and Persistence-
UnitUtil#getIdentifier(e) returns the value of the entity’s identifier property.
You can get to the PersistenceUnitUtil from the EntityManagerFactory.

There are two issues to look out for. First, be aware that the identifier value may not
be assigned and available until the persistence context is flushed. Second, Hibernate
(unlike some other JPA providers) never returns null from Persistence-
UnitUtil#getIdentifier() if your identifier property is a primitive (a long and not
a Long).



    */
