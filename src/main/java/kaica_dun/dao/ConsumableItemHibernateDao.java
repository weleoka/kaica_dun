package kaica_dun.dao;


import kaica_dun.entities.ItemConsumable;

import java.util.List;


public class ConsumableItemHibernateDao
        extends DaoGenericHibernate<ItemConsumable, Long>
        implements  ConsumableItemDao {


    @Override
    public Long create(ItemConsumable newInstance) {
        return null;
    }

    @Override
    public ItemConsumable read(Long aLong) {
        return null;
    }

    @Override
    public void update(ItemConsumable transientObject) {

    }

    @Override
    public void delete(ItemConsumable persistentObject) {

    }

    @Override
    public List<ItemConsumable> findByExample(ItemConsumable exampleInstance) {
        return null;
    }
}