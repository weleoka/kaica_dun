package kaica_dun.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


/**
 * Sets up the configuration for hibernate and lets the application
 * get current session objects easily.
 */
public class SessionUtil {
    private static final Logger log = LogManager.getLogger();

    private static SessionFactory sessionFactory;

    private static Session session;


    /**
     * Gets the current session.
     * If there is no session set a new one is opened.
     *
     * todo: confirm that the caught exceptions are from, and only from, expected causes.
     *
     * @return session          a Hibernate Session object
     */
    public static Session getSession() {

        // Please: this needs working on and there is a twist:
        //  https://stackoverflow.com/questions/19971098/nullpointerexception-in-sessionfactory-opensession

        try {
            session = sessionFactory.getCurrentSession();

        } catch (HibernateException e) {
            log.info("No current session available. Staring a new one.");
            session = sessionFactory.openSession();

        } catch (NullPointerException e) {
            log.info("A session is already open returning the same instance.");
            session = sessionFactory.openSession();
        }

        return session;
    }


    /**
     * Close the session.
     *
     * todo: consider when and why a outstnading transaction should be committed before closing
     *  a session.
     */
    public static void closeSession() {

        //log.debug("Commit the transaction.");
        //tr.commit();    // Close the transaction

        session.close();
    }




    /**
     * A SessionFactory is set up once for an application!
     *
     * @throws ExceptionInInitializerError
     */
    public static void setUpSessionFactory() throws ExceptionInInitializerError {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            log.error("Initial SessionFactory creation failed: " + e);
            // The registry would normally be destroyed by the SessionFactory,
            //      but we had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * This is just as a matter of course as only
     * one sessionFactory is ever needed for the application.
     */
    public static void closeDownSessionFactory() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }


    // Disabled this because probably never needed.
    // public static SessionFactory getSessionFactory() { return sessionFactory; }
}