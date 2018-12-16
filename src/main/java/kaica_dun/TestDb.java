package main.java.kaica_dun;
import main.java.kaica_dun.Entities.Person;
import org.hibernate.Session;


/**
 * Class created by Kai Weeks to test logging and application persistance systems.
 *
 * Sourced at https://www.concretepage.com/hibernate-4/hibernate-4-annotation-example-with-gradle
 *
 * Files concerning this work:
 * - src/main/java/kaica_dun/HibernateUtil.java
 * - src/main/java/kaica_dun/Entities/Person.java
 *
 * todo: Now find the hibernate configuration file that will have database connection setting and entity registration.
 *  hibernate.cfg.xml
 *
 */
public class TestDb {

    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1);
        person.setName("Concretepage");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(person);
        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }

}

