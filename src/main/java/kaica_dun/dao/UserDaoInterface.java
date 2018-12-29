package kaica_dun.dao;

import kaica_dun_system.User;

public interface UserDaoInterface extends DaoInterface<User, Long> {

    //T findByName(NAME name);
    User findByName(String name);
}


