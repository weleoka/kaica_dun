package kaica_dun.dao;
import java.util.List;
import java.io.Serializable;


/**
 * An interface shared by all business data access objects.
 * It is intended to be used as one interface per persistent entity,
 * with a super interface for common CRUD functionality and that is this file.
 *
 * All CRUD (create, read, update, delete) basic data access operations are found in this interface.
 *
 * This interface is designed to work with types T and ID.
 * T and ID are what's known as type variables, when you instantiate any class that implements this,
 * you provide actual types in lieu of those variables: whatever type you provide in place of T,
 * the findById method will return instances of that type, and findAll will return a List of that type.
 *
 * The findById method takes in an instance of whatever you substitute for ID. Note that whatever
 * type you give for ID must implement Serializable.
 *
 * http://www.giuseppeurso.eu/en/dao-factory-patterns-with-hibernate/
 *
 * todo: if the object has changed in its persistant state then checks for that should
 *  be made before the object is updated. Very Important if there are multiple systems
 *  working with the same database.
 *
 * todo: implement serializable?? Whats the benefit of this currently?
 *
 */


public interface DaoInterface<T, ID extends Serializable> {

    /** Persist the newInstance object into database */
    ID create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key.
     */
    T read(ID id);

    /** Save changes made to a persistent object.  */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);

    T findById(ID id, boolean lock);

    List<T> findAll();

    List<T> findByExample(T exampleInstance);

    T makePersistent(T entity);

    void makeTransient(T entity);
}
