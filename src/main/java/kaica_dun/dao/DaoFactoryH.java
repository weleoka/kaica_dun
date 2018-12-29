package kaica_dun.dao;

import kaica_dun_system.SessionUtil;
import org.hibernate.Session;

public class DaoFactoryH extends DaoFactory {

    @SuppressWarnings("unchecked")
    private AbstractHDao instantiateDao(Class daoClass) {
        try {
            AbstractHDao dao = (AbstractHDao) daoClass.getDeclaredConstructor().newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    @SuppressWarnings("unchecked")
    private MainHDao instantiateMainDao(Class daoClass) {
        try {
            MainHDao dao = (MainHDao) daoClass.getDeclaredConstructor().newInstance();
            Session session = getCurrentSession();

            session.clear();
            //session.beginTransaction();

            dao.setSession(session);
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate the main DAO: " + daoClass, ex);
        }
    }

    // You could override this if you don't want HibernateUtil for lookup
    protected Session getCurrentSession() {
        return SessionUtil.getInstance().getSession();
    }

    @Override
    public UserDaoInterface getUserDao() {
        return (UserDaoInterface) instantiateDao(UserHDao.class);
    }

    @Override
    public MainHDao getMainHDao() {
        return (MainHDao) instantiateMainDao(MainHDao.class);
    }

}
