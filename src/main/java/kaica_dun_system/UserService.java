package kaica_dun_system;


public interface UserService {

    // ********************** Checking Methods ********************** //

    boolean checkNewUserName(String userName);

    boolean isAuthenticatedUser();

    Long getSelectedUserID();

    User getSelectedUser();

    boolean loginSelectedUser();

    void logoutSelectedUser();

}
