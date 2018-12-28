package kaica_dun.dao;


import kaica_dun.entities.Item;
import kaica_dun.entities.Avatar;
import java.util.List;


public class AvatarHibernateDao
        extends DaoGenericHibernate<Avatar, Long>
        implements AvatarDao {


    @Override
    public Long create(Avatar newInstance) {
        return null;
    }

    @Override
    public Avatar read(Long aLong) {
        return null;
    }

    @Override
    public void update(Avatar transientObject) {
    }

    @Override
    public void delete(Avatar persistentObject) {
    }

    @Override
    public List<Avatar> findByExample(Avatar exampleInstance) {
        return null;
    }

    @Override
    public void moveItem(Avatar currentPlayer, Item inventoryItem) {

    }
}