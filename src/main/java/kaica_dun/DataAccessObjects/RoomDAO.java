package main.java.kaica_dun.DataAccessObjects;

import main.java.kaica_dun.Entities.Room;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Example code from: https://www.sitepoint.com/hibernate-introduction-persisting-java-objects/
 *
 * To be modified. I'm not sure that Room needs a DAO, possibly nice to have for the
 * Monster and Item.
 */
public class RoomDAO {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public RoomDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    //Replace the parameters with a call to random value constructor? Or solve in the Room class?
    public void persist(String name, String email) {
        beginTransaction();
        Room user = new Room(); //name, email);
        entityManager.persist(user);
        commitTransaction();
    }

    public Room find(int id) {
        return entityManager.find(Room.class, id);
    }


    public void update(int id, String name, String email) {
        beginTransaction();
        Room room = entityManager.find(Room.class, id);
        //room.setName(name);
        //room.setEmail(email);
        entityManager.merge(room);
        commitTransaction();
    }

    public void remove(int id) {
        beginTransaction();
        Room user = entityManager.find(Room.class, id);
        entityManager.remove(user);
        commitTransaction();
    }

    private void beginTransaction() {
        try {
            entityTransaction.begin();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void commitTransaction() {
        try {
            entityTransaction.commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void rollbackTransaction() {
        try {
            entityTransaction.rollback();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}