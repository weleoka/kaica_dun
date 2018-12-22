package kaica_dun.resources;


import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.MonsterDao;
import kaica_dun.dao.RoomDao;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Player;
import kaica_dun.entities.Room;
import kaica_dun.util.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * A class used for testing development issues.
 *
 * Primarily concerning persistence issues but can be used for logical
 * operation tests too.
 */
public class TestDb {

    // The application logger is set here.
    private static final Logger log = LogManager.getLogger();


    /**
     * This class is static and can be called directly.
     *
     * @param args              an array of strings of arguments
     */
    public static void main(String[] args) {

        log.info("MONSTER_CREATION_TEST:");
        testDb_Monster();

        log.info("MONSTER_FINDER_TEST:");
        testDb_MonsterFinder();

        log.info("DUNGEON_TEST:");
        testDb_Dungeon();
    }


    /**
     * Testing creation of monsters..!
     */
    public static void testDb_Monster() {
        Session session = SessionUtil.getSession();
        log.debug("Fetched a session.");

        // Make 5 monsters - get ready to run!!
        for (int i = 0; i < 4; i++) {
            Monster monster = MonsterFactory.makeMonster();
            session.save(monster);     // Question: why cant we tr.save(monster) ?
        }

        Util.sleeper(1200); // Artificial delay

        log.debug("Closing the session.");
        SessionUtil.closeSession(session);    // close the session
    }


    /**
     *  A test for searching for a monster using the DAO system.
     */
    public static void testDb_MonsterFinder() {
        Long monsterID = 1L;   // L is marks it as long
        Session session = SessionUtil.getSession();
        log.debug("Fetched a session.");


        /// Searching with only Hibernate
        log.debug("Using Hibernate no DAO to search for an entity by ID: " + monsterID);
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
        log.debug("Closing the session.");
        SessionUtil.closeSession(session);    // close the session
    }


    /**
     * Testing creation of static Dungeon
     */
    public static void testDb_Dungeon() {
        Session session = SessionUtil.getSession();
        log.debug("Fetched a session.");

        List<Dungeon> dl = new ArrayList<Dungeon>();
        //Make Player
        Player p = new Player("Carl", "password", dl);
        // Make static dungeon
        Dungeon d = new makeStaticDungeon(p).makeDungeon();
        session.save(d);

        Util.sleeper(1200); // Artificial sleep.

        log.debug("Closing the session.");
        SessionUtil.closeSession(session);    // close the session
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
