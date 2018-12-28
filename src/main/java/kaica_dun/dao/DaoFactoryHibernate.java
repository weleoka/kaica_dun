package kaica_dun.dao;

import kaica_dun_system.SessionUtil;
import org.hibernate.Session;

public class DaoFactoryHibernate extends DaoFactory {

    private DaoGenericHibernate instantiateDAO(Class daoClass) {
        try {
            DaoGenericHibernate dao = (DaoGenericHibernate) daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    // You could override this if you don't want HibernateUtil for lookup
    protected Session getCurrentSession() {
        return SessionUtil.getInstance().getSession();
    }


    @Override
    public RoomDao getRoomDao() {
        return null;
    }

    @Override
    public MonsterDao getMonsterDao() {
        return null;
    }
}
