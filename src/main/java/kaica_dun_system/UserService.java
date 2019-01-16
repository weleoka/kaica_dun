package kaica_dun_system;

import java.util.List;

public interface UserService {

    Long createUser(User user);

    User findUserById(Long userId);

    User findUserByName(String userName);

    List<User> findAll();



    // ********************** Checking Methods ********************** //

    boolean checkNewUserName(String userName);

    boolean loginUser(User user, String password);


    void printUserList();


}
