package kaica_dun.dao;

import kaica_dun_system.User;


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


