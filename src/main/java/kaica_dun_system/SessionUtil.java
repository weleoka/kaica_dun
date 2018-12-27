package kaica_dun_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * Currently uses the default JDBC transaction manager system
 * default via Hibernate. Recommended is to implement a JTA.
 *
 * Notes from Java Persistance with Hibernate 2nd Edition.
 * JTA provides a nice abstraction of the underlying resource’s transaction system, with
 * the added bonus of distributed system transactions. Many developers still believe
 * you can only get JTA with components that run in a Java EE application server. Today,
 * high-quality standalone JTA providers such as Bitronix (used for the example code of
 * this book) and Atomikos are available and easy to install in any Java environment.
 * Think of these solutions as JTA-enabled database connection pools.
 *
 * You should use JTA whenever you can and avoid proprietary transaction APIs
 * such as org.hibernate.Transaction or the very limited javax.persistence
 * .EntityTransaction. These APIs were created at a time when JTA wasn’t readily
 * available outside of EJB runtime containers.
 *
 * The following class is inspired by material from the repository at:
 * RESTfulGameServer-Hibernate on GitHub.
 *
 */
public class SessionUtil {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private static final int MAX_PENDING_SESSIONS = 4;
    private static List<Session> availableSessions = new ArrayList<Session>();

    private static final Logger log = LogManager.getLogger();

    public static synchronized Session getSession(){
        Session session = null;
        int lastIndex = availableSessions.size() - 1;

        if (availableSessions.isEmpty()) {
            // This is getting a reference to the calling class and is expensive.
            // This is for debugging how sessions are called for development only.
            String className = new Exception().getStackTrace()[1].getClassName();
            log.debug("Opened new session(id: 0) for class: '{}'.", className);
            session = sessionFactory.openSession();

        } else {
            log.debug("Re-using old session(id: {}).", lastIndex);
            session = availableSessions.get(lastIndex);
            availableSessions.remove(lastIndex);
        }

        try {
            session.beginTransaction();
            log.debug("Started new transaction.");

        } catch (IllegalStateException e) {
            log.debug("Transaction already exists in session(id: {}). Trying to get it..", lastIndex);
            session.getTransaction();
        }

        return session;
    }


    public static synchronized void closeSession(Session session) {
        String className = new Exception().getStackTrace()[1].getClassName();


        session.getTransaction().commit();

        if (availableSessions.size() <= MAX_PENDING_SESSIONS) {
            log.debug("Committed transaction for class '{}'. Not closing session.", className);
            availableSessions.add(session);

        } else {
            log.debug("Committed transaction for class '{}'. Closing session (availableSessions list is full).", className);
            session.close();
        }
    }
}
