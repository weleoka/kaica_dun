package kaica_dun.dao;


import kaica_dun_system.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;


import java.io.Serializable;
import java.util.List;


@NamedQuery(name="User.findByName", query="SELECT u FROM User u WHERE u.userName = :name")
public class UserHibernateDao extends DaoGenericHibernate<User, Long> implements UserDao {

    private static final Logger log = LogManager.getLogger();

    @Override
    public Long create(User newInstance) {
        log.debug("Creating a new user in db.");

        Session session = getSession();
        Long result = -1L;

        try {
            Serializable ser = session.save(newInstance);

            if (ser != null) {
                result = (Long) ser;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();

        return result;

        // long lastId = session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult().longValue();

    }

    @Override
    public User read(Long aLong) {
        Session session = getSession();
        User user = session.get(User.class, aLong);
        session.getTransaction().commit();

        return user;
    }


    @Override
    public void update(User transientObject) {
    }

    @Override
    public void delete(User persistentObject) {
    }


    /**
     * Read from storage and find a user by user name.
     *
     * https://stackoverflow.com/questions/25440284/fetch-database-records-using-hibernate-without-using-primary-key
     * https://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
     *
     * todo: Native SQL makes hibernate database implementation dependent.
     * todo: Calls Query#getSingleResult and that does not take into account multiple users with same name.
     *
     *
     * Customized query to retrieve a user by name using the JPA criteria API.
     *
     * For simple static queries - string based JPQL queries (e.g. as named queries) may be preferred.
     * For dynamic queries that are built at runtime - the criteria API may be preferred.
     *
     * JPQL queries are defined as strings, similarly to SQL. JPA criteria queries, on the other hand,
     * are defined by instantiation of Java objects that represent query elements.
     *
     * A major advantage of using the criteria API is that errors can be detected earlier, during compilation rather than at runtime.
     * On the other hand, for many developers string based JPQL queries, which are very similar to SQL queries,
     * are easier to use and understand. Using the criteria API introduces some extra work, at least for simple static queries.
     *
     * JPQL example: @NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
     *
     * https://www.objectdb.com/java/jpa/query/criteria // JPA criteria API
     * https://www.objectdb.com/java/jpa/query/named // JPA JPQL
     *
     * @param name a String to query with
     * @return a User instance
     */
    public User findByName(String name){
        Session session = getSession();

        // Method using JPA criteria API.
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from_class = query.from(User.class);
        ParameterExpression<String> param = builder.parameter(String.class);
        query.select(from_class).where(builder.equal(from_class.get("user_name"), param));

        TypedQuery<User> query_used = session.createQuery(query);
        query_used.setParameter(param, name);
        List<User> results = query_used.getResultList();

        return results.get(0);


        // Method using JPQL named query.
        // See annotation above class declaration.
        //TypedQuery<User> query = em.createNamedQuery("Country.findAll", User.class);
        //List<User> results = query.getResultList();
        // Another form of createNamedQuery receives a query name and returns a Query instance:
        //Query query = em.createNamedQuery("SELECT c FROM Country c");
        //List results = query.getResultList();



    }


    // Removed as it is existing in in userDao
    //public User findById(Long aLong) {
      //  return null;
    //}

    @Override
    public List<User> findByExample(User exampleInstance) {
        return null;
    }
}