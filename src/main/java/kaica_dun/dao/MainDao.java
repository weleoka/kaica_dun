package kaica_dun.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

//@Component
@Repository
public class MainDao<T, PK extends Serializable> extends AbstMainDao implements DaoInterface {

    private static final Logger log = LogManager.getLogger();

    @SuppressWarnings("unchecked")
    public MainDao(Class<T> entityClass) {
       super(entityClass);
    }


}