package kaica_dun_system;


import java.util.List;

public interface UserService {

    Long createUser(User user);

    User findById(Long userId);

    User findByName(String userName);

    public List<User> findAll();


    // ********************** Checking Methods ********************** //

    boolean checkNewUserName(String userName);

    boolean isAuthenticatedUser();

    Long getSelectedUserID();

    User getSelectedUser();

    boolean loginSelectedUser();


    void logoutSelectedUser();

}
