package kaica_dun.dao;


import kaica_dun.entities.ConsumableItem;

import java.util.List;


public class ConsumableItemHibernateDao
        extends DaoGenericHibernate<ConsumableItem, Long>
        implements  ConsumableItemDao {


    @Override
    public Long create(ConsumableItem newInstance) {
        return null;
    }

    @Override
    public ConsumableItem read(Long aLong) {
        return null;
    }

    @Override
    public void update(ConsumableItem transientObject) {

    }

    @Override
    public void delete(ConsumableItem persistentObject) {

    }

    @Override
    public List<ConsumableItem> findByExample(ConsumableItem exampleInstance) {
        return null;
    }
}