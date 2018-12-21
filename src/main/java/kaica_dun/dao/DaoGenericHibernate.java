package kaica_dun.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * This implementation needs a Session to work, provided with setter injection or
 * you could also use constructor injection. How you set the Session and what scope
 * this Session has is of no concern to the actual DAO implementation.
 *
 * A DAO should not control transactions or the Session scope.
 */
public abstract class DaoGenericHibernate<T, ID extends Serializable> implements DaoGenericInterface<T, ID> {

    private Class<T> persistentClass;
    private Session session;

    public DaoGenericHibernate() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public void setSession(Session s) {
        this.session = s;
    }

    protected Session getSession() {
        if (session == null)
            throw new IllegalStateException("Session has not been set on DAO before usage");
        return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     *
     * Note on implementation. I changed the hibernate call from .load() to get() for easier
     * returning null if item is not found
     *
     * @param id
     * @param lock          currently disabled as Hibernate will handles this very well.
     * @return
     */
    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getSession().get(getPersistentClass(), id);
            //entity = (T) getSession().load(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) getSession().get(getPersistentClass(), id);
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {

        //Open Session
        Session session = getSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<T> criteria = builder.createQuery(getPersistentClass());
        Root<T> contactRoot = criteria.from(getPersistentClass());
        criteria.select(contactRoot);

        //Use criteria to query with session to fetch all contacts
          List<T> tmpList = session.createQuery(criteria).getResultList();

        //Close session
        session.close();


        // The following is used to exclude properties from query.
        /*Example example =  Example.create(exampleInstance);

        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }*/


        return tmpList;



    }

    @SuppressWarnings("unchecked")
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