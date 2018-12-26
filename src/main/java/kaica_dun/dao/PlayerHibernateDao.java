package kaica_dun.dao;


import kaica_dun_system.User;

import java.util.List;


public class PlayerHibernateDao
        extends DaoGenericHibernate<User, Long>
        implements  PlayerDao {


    @Override
    public Long create(User newInstance) {
        return null;
    }

    @Override
    public User read(Long aLong) {
        return null;
    }

    @Override
    public void update(User transientObject) {
    }

    @Override
    public void delete(User persistentObject) {
    }

    @Override
    public List<User> findByExample(User exampleInstance) {
        return null;
    }
}