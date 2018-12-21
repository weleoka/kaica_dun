package kaica_dun;

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
    private final Logger logger = LogManager.getLogger();

    private static SessionFactory sessionFactory;




    public void setUpSessionFactory() throws ExceptionInInitializerError {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            this.logger.error("Initial SessionFactory creation failed: " + e);
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError(e);
        }
    }




    public void closeDownSessionFactory() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }




    /**
     * Gets the current session. If there is no session set it opens a new one.
     *
     * @return
     */
    public Session getCurrentSession() {
        Session session;

        try {
            session = sessionFactory.getCurrentSession();

        } catch (HibernateException e) {
            this.logger.info("No current session available. Staring a new one.");
            session = sessionFactory.openSession();
        }

        return session;
    }




    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}