package kaica_dun.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;


public class MainHDao<T, ID extends Serializable> implements DaoInterface<T, ID> {

    private static final Logger log = LogManager.getLogger();
    private Class<T> persistentClass;
    private Session session;

/*    @SuppressWarnings("unchecked")
    public MainHDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }*/

    public void setSession(Session s) {
        this.session = s;
    }

    public Session getSession() {
        if (session == null)
            throw new IllegalStateException("Session has not been set on DAO before usage");
        //session.clear();  // This may be a really bad idea but works for now.
        return session;
    }

    public Class<T> getPersistentClass() {
        return null; //this.persistentClass;
    }

    /**
     *
     * Note on implementation. I changed the hibernate call from .load() to get() for easier
     * returning null if item is not found
     *
     * @param id
     * @param lock          currently disabled as Hibernate handles this very well.
     * @return
     */
    public T findById(ID id, boolean lock) {
        T entity;
        Session session = getSession();
        if (lock) {
            entity = (T) getSession().get(getPersistentClass(), id);
            //entity = (T) getSession().load(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) getSession().get(getPersistentClass(), id);
        }
        session.getTransaction().commit();
        return entity;
    }

    /**
     *
     * @return
     * @deprecated
     */
    public List<T> findAllOld() {
        return findByCriteria();
    }

    /**
     * This is the new version of finding all as the old hibernate
     * session.createCriteria is depreciated.
     *
     * Not sure if it can be made to look a little nicer perhaps.
     *
     * @author Kai Weeks
     */
    public List<T> findAll() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getPersistentClass());
        Root<T> from_class = query.from(getPersistentClass());
        ParameterExpression<String> param = builder.parameter(String.class);

        query.select(from_class);

        TypedQuery<T> query_used = session.createQuery(query);
        //session.flush();
        List<T> results = query_used.getResultList();
        session.getTransaction().commit();

        return results;
    }

    @Override
    @Deprecated
    public List<T> findByExample(T exampleInstance) {
        return null;
    }

    @Override
    public T read(ID aLong) {
        Session session = getSession();
        T entity = session.get(getPersistentClass(), aLong);
        session.getTransaction().commit();

        return entity;
    }

    @Override
    public ID create(T entity) {
        log.debug("Creating a {} in db.", entity);

        Session session = getSession();
        ID result = null;

        try {
            Serializable ser = session.save(entity);
            session.flush();
            //session.getTransaction().commit();

            if (ser != null) {
                result = (ID) ser;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public void update(T transientObject) {
    }

    @Override
    public void delete(T persistentObject) {
    }

    public T makePersistent(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public void makeTransient(T entity) {
        getSession().delete(entity);
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }


    /**
     * Use this inside subclasses as a convenience method.
     * @deprecated
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }
}
