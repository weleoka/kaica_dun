package kaica_dun;

import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.RoomDao;
import kaica_dun.entities.Player;
import kaica_dun.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 */
public class TestDb {
    private final Logger LOGGER = LogManager.getLogger();
    private final SessionUtil sessionUtil = new SessionUtil();

    public void main(String[] args) throws Exception {
        testDb();
    }

    public Session beginSession() {
        Logger l = this.LOGGER; // Convenience formatting for readability.

        l.debug("Starting new SessionFactory.");

        try {
            sessionUtil.setUpSessionFactory();
        } catch (Exception e) {
            l.warn(e);
        }

        l.debug("Getting session from factory.");
        Session session = sessionUtil.getCurrentSession();   // Open the session
        return session;

    }


    /**
     * Currently having troubles with the DAO as due to it implementing serializable it requires a Long yet all the
     * ID are in the DB as int. Cosisder changing the ddl to use long int for this??
     *
     */
    public void testDb()  {
        Logger l = this.LOGGER; // Convenience formatting for readability.

        Long roomID = Long.valueOf(11);

        Session sess = beginSession();

        /// Searching with only Hibernate
        l.debug("Using Hibernate no DAO to search for an entity by ID: " + roomID);
        Room room = (Room) sess.get(Room.class, roomID);

        l.debug("Room found: " + room.toString());



        /// Searching with the DAO process
        l.debug("Starting up a DAO Factory for Hibernate infrastructure.");
        DaoFactory daoFactory = DaoFactory.instance(DaoFactory.HIBERNATE);

        l.debug("Getting a specific DAO (RoomDao) from Hibernate DAO Factory.");
        RoomDao roomDao = daoFactory.getRoomDao();

        l.debug("Using DAO to search for an entity by ID: " + roomID);
        try {
            Room roomFoundByDao = roomDao.findById(roomID, true);
            l.debug("Found a Room by ID: " + roomFoundByDao.toString());
        }
        catch (NullPointerException e) {
            l.debug("No room found with that ID: " + e);
        }





        l.debug("Beginning new transaction in session.");
        Transaction tr = sess.beginTransaction(); // Open the transaction

        // Set the room id without calling session.save()
        //l.debug("Changing the value of one of the entity attributes.");
        //room.setRoomId(999);

        l.debug("Commit the transaction.");
        tr.commit();    // Close the transaction

        l.debug("Closing the session.");
        sessionUtil.getCurrentSession().close();    // close the session

        l.debug("Closing the SessionFactory.");
        sessionUtil.closeDownSessionFactory();   // Close the SessionFactory

        Player player = new Player();
        player.setPlayerName("Magic Mike");
        player.setPassword("testing");


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