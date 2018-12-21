package kaica_dun.resources;

import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.RoomDao;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Player;
import kaica_dun.entities.Room;
import kaica_dun.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        //testDb_Room();
        testDb_Dungeon();
        //testDb_Monster();
    }


    /**
     * Testing creation of monsters..!
     */
    public static void testDb_Monster() {
        Session session = SessionUtil.getSession();

        log.debug("Beginning new transaction in session.");
        Transaction tr = session.beginTransaction(); // Open the transaction

        // Make 5 monsters - get ready to run!!
        for (int i = 0; i < 4; i++) {
            Monster monster = MonsterFactory.makeMonster();
            session.save(monster);     // Question: why cant we tr.save(monster) ?
        }

        // Sleep for a while to see how the transaction is done
        //      is it in a burst or at each sess.save() call?
        Util.sleeper(1200);


        log.debug("Commit the transaction.");
        tr.commit();    // Close the transaction

        log.debug("Closing the session.");
        SessionUtil.closeSession();    // close the session

        // Disabled because we dont have to close the session except on program exit.
        //log.debug("Closing the SessionFactory.");
        //SessionUtil.closeDownSessionFactory();   // Close the SessionFactory

    }


    /**
     *  A test for searching for a room ID using the DAO system.
     */
    public static void testDb_Room() {
        Long roomID = Long.valueOf(1);
        Session session = SessionUtil.getSession();

        log.debug("Beginning new transaction in session.");
        Transaction tr = session.beginTransaction(); // Open the transaction


        /// Searching with only Hibernate
        log.debug("Using Hibernate no DAO to search for an entity by ID: " + roomID);
        Room room = session.get(Room.class, roomID);

        if (room != null) { // Have to test if this works, am I forgetting something.
            log.debug("Room found: " + room.toString());
        }


        /// Searching with the DAO processes
        // Many options for DAO's can exist. Select the Hibernate one.
        log.debug("Using DAO to search for an entity by ID: " + roomID);
        log.debug("Starting up a DAO Factory for Hibernate infrastructure.");
        DaoFactory daoFactory = DaoFactory.instance(DaoFactory.HIBERNATE);

        log.debug("Getting a specific DAO (RoomDao) from Hibernate DAO Factory.");
        RoomDao roomDao = daoFactory.getRoomDao();

        log.debug("Searching for roomID: " + roomID);

        try {
            Room roomFoundByDao = roomDao.findById(roomID, true);
            log.debug("Found a Room by ID: " + roomFoundByDao.toString());

        } catch (NullPointerException e) {
            log.debug("No room found with that ID: " + e);
        }

        // Sleep for a while to see how the transaction is done.
        Util.sleeper(1200);


        log.debug("Commit the transaction.");
        tr.commit();    // Close the transaction

        log.debug("Closing the session.");
        SessionUtil.closeSession();    // close the session
    }

    /**
     * Testing creation of static Dungeon
     */
    public static void testDb_Dungeon() {
        Session session = SessionUtil.getSession();

        log.debug("Beginning new transaction in session.");
        Transaction tr = session.beginTransaction(); // Open the transaction

        List<Dungeon> dl = new ArrayList<Dungeon>();
        //Make Player
        Player p = new Player("Carl", "password", dl);
        // Make static dungeon
        Dungeon d = new makeStaticDungeon(p).makeDungeon();
        session.save(d);
        // Sleep for a while to see how the transaction is done
        //      is it in a burst or at each sess.save() call?
        Util.sleeper(1200);


        log.debug("Commit the transaction.");
        tr.commit();    // Close the transaction

        log.debug("Closing the session.");
        SessionUtil.closeSession();    // close the session

        // Disabled because we dont have to close the session except on program exit.
        //log.debug("Closing the SessionFactory.");
        //SessionUtil.closeDownSessionFactory();   // Close the SessionFactory

    }

    /* This is another example of usage of session and sessionFactory
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
    */
}