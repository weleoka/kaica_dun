package kaica_dun_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SessionUtil {

    // Singleton
    private static SessionUtil ourInstance = new SessionUtil();
    public static SessionUtil getInstance() {
        return ourInstance;
    }
    private SessionUtil() {
    }

    // Fields declared
    private static final Logger log = LogManager.getLogger();

    // Multi threaded Session management
    private static final int MAX_PENDING_SESSIONS = 4;
    private static List<Session> availableSessions = new ArrayList<Session>();

    // Basic session management
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private Session session = null;


    /**
     * Method for getting the active session and starting a transaction if none is active.
     *
     * @return session Session object
     */
    public Session getSession() {
        String className = "Not_identified.";
        className = new Exception().getStackTrace()[1].getClassName();
        log.debug("Fetching session for class: '{}'.", className);

        Session sess = null;
        try {
            sess = sessionFactory.getCurrentSession();

        } catch (org.hibernate.HibernateException he) {
            sess = sessionFactory.openSession();
        }

        sess.beginTransaction();
        return sess;
    }




    /*

    void begin() starts a new transaction.
    void commit() ends the unit of work unless we are in FlushMode.NEVER.
    void rollback() forces this transaction to rollback.
    void setTimeout(int seconds) it sets a transaction timeout for any transaction started by a subsequent call to begin on this instance.
    boolean isAlive() checks if the transaction is still alive.
    void registerSynchronization(Synchronization s) registers a user synchronization callback for this transaction.
    boolean wasCommited() checks if the transaction is commited successfully.
    boolean wasRolledBack() checks if the transaction is rolledback successfully.

     */




    /**
     * Method for holding a session pool for multi threaded applications.
     *
     * @return session a Session object
     */
/*    public synchronized Session getSessionMulti() {
        Session session = null;
        String className = "Not_identified.";

        log.debug("Current sessions in availableSessions list: {}", availableSessions.size());
        int lastIndex = availableSessions.size() - 1;

        if (availableSessions.isEmpty()) {
            // This is getting a reference to the calling class and is expensive.
            // This is for debugging how sessions are called for development only.
            className = new Exception().getStackTrace()[1].getClassName();

            log.debug("Opened new session(id: 0) for class: '{}'.", className);


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
    }*/

    /**
     * Mwthod for closingSession if there are too many after committing,
     * or keeping the session in a pool if there are not too many.
     * @param session
     */
  /*  public synchronized void closeSession(Session session) {
        String className = new Exception().getStackTrace()[1].getClassName();
        session.getTransaction().commit();

        if (availableSessions.size() <= MAX_PENDING_SESSIONS) {
            log.debug("Committed transaction for class '{}'. Not closing session.", className);
            availableSessions.add(session);

        } else {
            log.debug("Committed transaction for class '{}'. Closing session (availableSessions list is full).", className);
            session.close();
        }
    }*/
}
