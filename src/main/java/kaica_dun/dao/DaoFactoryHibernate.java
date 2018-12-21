package kaica_dun.dao;


import kaica_dun.resources.SessionUtil;
import org.hibernate.Session;


public class DaoFactoryHibernate extends DaoFactory {

    /**
     *
     * IllegalArgumentException, NoSuchMethodException, and InvocationTargetException
     * @param daoClass
     * @return
     */
    private DaoGenericHibernate instantiateDao(Class daoClass) {

        try {
            DaoGenericHibernate dao = (DaoGenericHibernate)daoClass.getConstructor().newInstance();
            dao.setSession(getCurrentSession());

            return dao;

        } catch (Exception ex) {

            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    // You could override this if you don't want SessionUtil for lookup
    protected Session getCurrentSession() {
        return SessionUtil.getSession();
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
