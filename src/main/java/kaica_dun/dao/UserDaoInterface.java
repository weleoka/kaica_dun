package kaica_dun.dao;

import kaica_dun_system.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDaoInterface extends DaoInterface<User, Long> {

    //T findByName(NAME name);

    /**
     * @Depreciated testing use of the new method findByUsername
     */
    User findByName(String name);

    //User findByUsername(String name);

    //@Transactional
    //@Query("SELECT user FROM User user WHERE user.username=(:username)")
    //User findByUsername(@Param("username") String userName);
}


