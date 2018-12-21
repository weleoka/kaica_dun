package kaica_dun.dao;


import kaica_dun.entities_BACKUP.Dungeon;

import java.util.List;


public class DungeonHibernateDao
        extends DaoGenericHibernate<Dungeon, Long>
        implements  DungeonDao {


    @Override
    public Long create(Dungeon newInstance) {
        return null;
    }

    @Override
    public Dungeon read(Long aLong) {
        return null;
    }

    @Override
    public void update(Dungeon transientObject) {
    }

    @Override
    public void delete(Dungeon persistentObject) {

    }

    @Override
    public List<Dungeon> findByExample(Dungeon exampleInstance) {
        return null;
    }
}