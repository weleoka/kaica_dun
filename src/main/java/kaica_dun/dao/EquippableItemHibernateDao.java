package kaica_dun.dao;


import kaica_dun.entities_BACKUP.EquippableItem;

import java.util.List;


public class EquippableItemHibernateDao
        extends DaoGenericHibernate<EquippableItem, Long>
        implements  EquippableItemDao {


    @Override
    public Long create(EquippableItem newInstance) {
        return null;
    }

    @Override
    public EquippableItem read(Long aLong) {
        return null;
    }

    @Override
    public void update(EquippableItem transientObject) {

    }

    @Override
    public void delete(EquippableItem persistentObject) {

    }

    @Override
    public List<EquippableItem> findByExample(EquippableItem exampleInstance) {
        return null;
    }
}