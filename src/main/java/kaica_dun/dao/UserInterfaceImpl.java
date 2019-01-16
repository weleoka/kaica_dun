package kaica_dun.dao;

import kaica_dun_system.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserInterfaceImpl implements UserInterfaceCustom {

    private static final Logger log = LogManager.getLogger();

    //@Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Read from storage and find a user by user name.
     * <p>
     * https://stackoverflow.com/questions/25440284/fetch-database-records-using-hibernate-without-using-primary-key
     * https://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
     * <p>
     * todo: Native SQL makes hibernate database implementation dependent.
     * todo: Calls Query#getSingleResult and that does not take into account multiple users with same name.
     * <p>
     * <p>
     * Customized query to retrieve a user by name using the JPA criteria API.
     * <p>
     * For simple static queries - string based JPQL queries (e.g. as named queries) may be preferred.
     * For dynamic queries that are built at runtime - the criteria API may be preferred.
     * <p>
     * JPQL queries are defined as strings, similarly to SQL. JPA criteria queries, on the other hand,
     * are defined by instantiation of Java objects that represent query elements.
     * <p>
     * A major advantage of using the criteria API is that errors can be detected earlier, during compilation rather than at runtime.
     * On the other hand, for many developers string based JPQL queries, which are very similar to SQL queries,
     * are easier to use and understand. Using the criteria API introduces some extra work, at least for simple static queries.
     * <p>
     * JPQL example: @NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
     * <p>
     * https://www.objectdb.com/java/jpa/query/criteria // JPA criteria API
     * https://www.objectdb.com/java/jpa/query/named // JPA JPQL
     *
     * @param name a String to query with
     * @return a User instance
     */
    //@Override
    @Transactional
    public List<User> findByName(String name) {
        //EntityManager em = getEntityManager();
        // Method using JPQL named query and EntityManager
        //Named query is at annotation on class

        TypedQuery<User> query = this.entityManager.createNamedQuery("User.findByName", User.class);
        query.setParameter("name", name);
        List<User> results = query.getResultList();

        if (results.size() == 1) {
            return results;
        } else if (results.size() == 0) {
            log.debug("No user found with name: {}", name);
        } else {
            log.warn("More than one user with name: {}", name);
        }

        return results;
        /* // Method using JPA criteria API.
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from_class = query.from(User.class);
        ParameterExpression<String> param = builder.parameter(String.class);
        query.select(from_class).where(builder.equal(from_class.get("userName"), param));

        TypedQuery<User> query_used = session.createQuery(query);
        query_used.setParameter(param, name);
        List<User> results = query_used.getResultList();
        */


        // Hibernate namedQuery and annotations

        //TypedQuery<User> query = getEntityManager().getNamedQuery("User.findByName");
        //User result = query.setParameter("name", name).getSingleResult();
        // Create query in code.
        //TypedQuery<User> que = session.createNamedQuery("User.findByName", User.class);


        // Another form of createNamedQuery receives a query name and returns a Query instance:
        //Query query = em.createNamedQuery("SELECT c FROM Country c");
        //List results = query.getResultList();
        //session.getTransaction().commit();
    }
}
