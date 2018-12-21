package kaica_dun.dao;


import kaica_dun.entities_BACKUP.Item;
import kaica_dun.entities_BACKUP.PlayerAvatar;
import java.util.List;


public class PlayerAvatarHibernateDao
        extends DaoGenericHibernate<PlayerAvatar, Long>
        implements  PlayerAvatarDao {


    @Override
    public Long create(PlayerAvatar newInstance) {
        return null;
    }

    @Override
    public PlayerAvatar read(Long aLong) {
        return null;
    }

    @Override
    public void update(PlayerAvatar transientObject) {
    }

    @Override
    public void delete(PlayerAvatar persistentObject) {
    }

    @Override
    public List<PlayerAvatar> findByExample(PlayerAvatar exampleInstance) {
        return null;
    }

    @Override
    public void moveItem(PlayerAvatar currentPlayer, Item inventoryItem) {

    }
}