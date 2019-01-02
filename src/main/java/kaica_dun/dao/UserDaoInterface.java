package kaica_dun.dao;

import kaica_dun_system.User;
import org.springframework.stereotype.Repository;

public interface UserDaoInterface {//extends DaoInterface<User, Long> {

    //T findByName(NAME name);

    User findByName(String name);

    //User findByUsername(String name);

    //@Transactional
    //@Query("SELECT user FROM User user WHERE user.username=(:username)")
    //User findByUsername(@Param("username") String userName);
}


