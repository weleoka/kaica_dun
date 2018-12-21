package kaica_dun.dao;


import kaica_dun.entities.Player;

import java.util.List;


public class PlayerHibernateDao
        extends DaoGenericHibernate<Player, Long>
        implements  PlayerDao {


    @Override
    public Long create(Player newInstance) {
        return null;
    }

    @Override
    public Player read(Long aLong) {
        return null;
    }

    @Override
    public void update(Player transientObject) {
    }

    @Override
    public void delete(Player persistentObject) {
    }

    @Override
    public List<Player> findByExample(Player exampleInstance) {
        return null;
    }
}