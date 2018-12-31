package kaica_dun.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;


public abstract class AbstMainDao<T, PK extends Serializable> implements DaoInterface<T, PK> {

    protected Class<T> entityClass;

    protected EntityManager entityManager;

    //@PersistenceContext
    //private SessionFactory sessionFactory;


    public AbstMainDao() {
    }

    @SuppressWarnings("unchecked")
    public AbstMainDao(Class<T> entityClass) {
        this.entityClass = entityClass;

        //this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        //ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        //this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return this.entityClass;
    }




    protected EntityManager getEntityManager() {
        return this.entityManager;
    }


    @Override
    public T create(T t) {
        getEntityManager().persist(t);
        return t;
    }

    @Override
    public T read(PK id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public T update(T t) {
        getEntityManager().merge(t);
        return t;
    }

    @Override
    public void delete(T t) {
        t = this.entityManager.merge(t);
        getEntityManager().remove(t);
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager(); // For readability

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query_base = builder.createQuery(getPersistentClass());
        Root<T> rootEntry = query_base.from(getPersistentClass());
        CriteriaQuery<T> all = query_base.select(rootEntry);
        TypedQuery<T> query_used = em.createQuery(all);
        return query_used.getResultList();
    }
}

/*
    @Override
    public Serializable save(Employee employee) {
        return getSession().save(employee);
    }

    @Override
    public Employee findById(final Serializable id) {
        return getSession().get(Employee.class, id);
    }
*/