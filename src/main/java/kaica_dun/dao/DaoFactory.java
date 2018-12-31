package kaica_dun.dao;

import org.hibernate.Session;

import java.io.Serializable;

public class DaoFactory<T, ID extends Serializable> {

    @SuppressWarnings("unchecked")
    private MainDao instantiateMainDao(Class daoClass) {
        try {
            return (MainDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate the main DAO: " + daoClass, ex);
        }
    }

    private UserDao instantiateUserDao() {
        try {
            return new UserDao(); //(UserDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate UserDAO.");
        }
    }

    public MainDao getMainDao(Class<T> entityClass) {
        return (MainDao) instantiateMainDao(entityClass);
    }

    public UserDao getUserDao() {
        return instantiateUserDao();
    }

    // You could override this if you don't want HibernateUtil for lookup
/*    protected Session getCurrentSession() {
        return null; //SessionUtil.getInstance().getSession();
    }*/
}
