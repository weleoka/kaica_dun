package kaica_dun.dao;


import kaica_dun.entities.Room;

import java.util.List;


public class RoomHibernateDao
        extends     GenericHibernateDao<Room, Long>
        implements  RoomDao {


    @Override
    public Long create(Room newInstance) {
        return null;
    }

    @Override
    public Room read(Long aLong) {
        return null;
    }

    @Override
    public void update(Room transientObject) {

    }

    @Override
    public void delete(Room persistentObject) {

    }

    @Override
    public List<Room> findByExample(Room exampleInstance) {
        return null;
    }
}