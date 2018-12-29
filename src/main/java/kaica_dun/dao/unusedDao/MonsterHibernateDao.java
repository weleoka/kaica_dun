package kaica_dun.dao.unusedDao;


import kaica_dun.dao.DaoGenericHibernate;
import kaica_dun.entities.Monster;

import java.util.List;


public class MonsterHibernateDao
        extends DaoGenericHibernate<Monster, Long>
        implements  MonsterDao {


    @Override
    public Long create(Monster newInstance) {
        return null;
    }

    @Override
    public Monster read(Long aLong) {
        return null;
    }

    @Override
    public void update(Monster transientObject) {
    }

    @Override
    public void delete(Monster persistentObject) {
    }

    @Override
    public List<Monster> findByExample(Monster exampleInstance) {
        return null;
    }
}