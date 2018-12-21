package kaica_dun.entities2;

import kaica_dun.SessionUtil;
import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.RoomDao;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 */
public class TestDb {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Logger l = LOGGER; // Convenience formatting for readability.

    private static final SessionUtil sessionUtil = new SessionUtil();


    public static void main(String[] args) throws Exception {
        testDb_Monster();
    }


    public static Session beginSession() {

        l.debug("Starting new SessionFactory.");

        try {
            sessionUtil.setUpSessionFactory();
        } catch (Exception e) {
            l.warn(e);
        }

        l.debug("Returning session from factory.");
        return sessionUtil.getCurrentSession();   // Open the session

    }

    public static void testDb_Monster() {
        Session sess = beginSession();

        l.debug("Beginning new transaction in session.");
        Transaction tr = sess.beginTransaction(); // Open the transaction


        MonsterFactory monsterFactory = new MonsterFactory();

        Monster monster = monsterFactory.makeMonster();
        Monster monster2 = monsterFactory.makeMonster();
        Monster monster3 = monsterFactory.makeMonster();
        Monster monster4 = monsterFactory.makeMonster();
        Monster monster5 = monsterFactory.makeMonster();

        sess.save(monster);     // Question: why cant we tr.save(monster); ????
        sess.save(monster2);
        sess.save(monster3);
        sess.save(monster4);
        sess.save(monster5);


        l.debug("Commit the transaction.");
        tr.commit();    // Close the transaction

        l.debug("Closing the session.");
        sessionUtil.getCurrentSession().close();    // close the session

        l.debug("Closing the SessionFactory.");
        sessionUtil.closeDownSessionFactory();   // Close the SessionFactory

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