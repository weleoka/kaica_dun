package kaica_dun_system;

import kaica_dun.entities.Avatar;

import java.util.List;
import java.util.Set;

public interface UserService {

    Long createUser(User user);

    User findUserById(Long userId);

    User findUserByName(String userName);

    List<User> findAll();

    void setCurrAvatar(Avatar avatar);



    // ********************** Checking Methods ********************** //

    boolean checkNewUserName(String userName);

    boolean isAuthenticatedUser();

    Long getAuthenticatedUserId();



    boolean loginUser(User user, String password);

    void logoutUser();



    void printUserList();


}
