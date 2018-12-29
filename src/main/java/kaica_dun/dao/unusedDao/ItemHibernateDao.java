package kaica_dun.dao.unusedDao;


import kaica_dun.dao.DaoGenericHibernate;
import kaica_dun.entities.Item;

import java.util.List;


public class ItemHibernateDao
        extends DaoGenericHibernate<Item, Long>
        implements  ItemDao {


    @Override
    public Long create(Item newInstance) {
        return null;
    }

    @Override
    public Item read(Long aLong) {
        return null;
    }

    @Override
    public void update(Item transientObject) {
    }

    @Override
    public void delete(Item persistentObject) {
    }

    @Override
    public List<Item> findByExample(Item exampleInstance) {
        return null;
    }
}