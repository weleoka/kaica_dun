package kaica_dun.dao;


import kaica_dun.entities.ItemEquippable;

import java.util.List;


public class EquippableItemHibernateDao
        extends DaoGenericHibernate<ItemEquippable, Long>
        implements  EquippableItemDao {


    @Override
    public Long create(ItemEquippable newInstance) {
        return null;
    }

    @Override
    public ItemEquippable read(Long aLong) {
        return null;
    }

    @Override
    public void update(ItemEquippable transientObject) {

    }

    @Override
    public void delete(ItemEquippable persistentObject) {

    }

    @Override
    public List<ItemEquippable> findByExample(ItemEquippable exampleInstance) {
        return null;
    }
}