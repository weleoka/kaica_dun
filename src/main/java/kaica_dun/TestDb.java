package kaica_dun;

import kaica_dun.Entities.Employee;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 */
public class TestDb {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public void main(String[] args) throws Exception {
        testDb();
    }


    public void testDb() throws Exception {
        this.LOGGER.debug("This is a log message.");
        HibernateUtil.setUpFactory();

        Session session = HibernateUtil.getCurrentSession();   // Open the session

        Transaction tr = session.beginTransaction(); // Open the transaction

        Employee emp = new Employee();
        emp.setEmpName("Tester Bob");
        emp.setEmpMobileNos("9191992");
        emp.setEmpAddress("Ingelstavägen 22");
        session.save(emp);  // Save the new object

        tr.commit();    // Close the transaction

        this.LOGGER.debug("Successfully inserted object to DB.");

        session.close();    // close the session

        HibernateUtil.closeDownFactory();   // Close the SessionFactory
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