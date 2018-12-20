package kaica_dun.dao;

public abstract class DaoFactory {

    /**
     * Creates a standalone DAOFactory that returns unmanaged DAO
     * beans for use in any environment Hibernate has been configured
     * for. Uses SessionUtil/SessionFactory and Hibernate context
     * propagation (CurrentSessionContext), thread-bound or transaction-bound,
     * and transaction scoped.
     */
    public static final Class HIBERNATE = DaoHibernateFactory.class;

    /**
     * Factory method for instantiation of concrete factories.
     * This is using reflection to create classes dynamicaly at runtime.
     */
    public static DaoFactory instance(Class factory) {
        try {
            return (DaoFactory)factory.getConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DAOFactory: " + factory);
        }
    }

    // Add your DAO interfaces here
    public abstract RoomDao getRoomDao();
}