package kaica_dun_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import javax.persistence.*;



/**
 * Currently handling details of reading and writing USer to a string for
 * saving in the database. The array is as follows:
 *
 * userArr[0] is the userName
 * userArr[1] is the userID
 * userArr[2] is the status
 *
 * todo: hash and salt of passwords.
 *
 */
public class UserControl {
    private User selectedUser;  // user object that is subject to operations.
    private User authenticatedUser; // holds a reference to the user object if isAuthenticated.
    private static final Logger log = LogManager.getLogger();
    private Session session = SessionUtil.getSession();

    private static UserControl ourInstance = new UserControl();

    static UserControl getInstance() {
        return ourInstance;
    }

    private UserControl() {}


    /**
     * Read from storage and find a user by user name.
     *
     * If found then set selectedUser to new instance with attributes set.
     *
     * https://stackoverflow.com/questions/25440284/fetch-database-records-using-hibernate-without-using-primary-key
     *
     * https://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
     *
     * todo: Native SQL makes hibernate database implementation dependent.
     * todo: Calls Query#getSingleResult and that does not take into account multiple users with same name.
     *
     * @param userName a String of the name too look for
     */
    boolean selectUserByUserName(String userName) {
        log.debug("Searching for user '%s' by name.", userName);

        Query query = this.session.createSQLQuery(
                "SELECT * FROM user u WHERE u.user_name LIKE :userName")
                .addEntity(User.class)
                .setParameter("userName", userName);
        User result = (User) query.getSingleResult(); // List<User> result = query.getResultList();

        this.selectedUser = result;

        // Need to use string formatting for logger.
        log.debug("Found a user by the name '{}'.", this.selectedUser.getName());

        return true;

    }


    /**
     * Read from storage and find a user by user id.
     * <p>
     * If found in DB then set selectedUser to relevant instance.
     *
     * @param userID a String of the users id to look for
     */
    boolean selectUserByUserID(String userID) {
        User user = this.session.get(User.class, userID);

        if (user != null) {
            this.selectedUser = user;

            return true;
        }

        return false;
    }


    /**
     * Checks if authenticatedUser is set and returns result
     *
     * @return boolean              true if user is set and thus logged in
     */
    boolean isAuthenticatedUser() {

        if (this.authenticatedUser == null) {

            return false;
        }

        return true;
    }


    /**
     * return the ID of the selected user
     *
     * @return userID           true if user is set and thus logged in
     */
    Long getSelectedUserID() {
        return this.selectedUser.getId();
    }

    User getSelectedUser() {
        return this.selectedUser;
    }


    /**
     * Check if the username is available
     */
    boolean checkNewUserName(String userName) {

        if (selectUserByUserName(userName)) {
            this.selectedUser = null;

            return false;
        }

        return true;
    }


    /**
     * setAuthenticated
     *
     * The selectedUser variable should already be set before this method is called.
     * This method implements a check that the selectedUser in the UserControl is indeed the same
     * object as is stored in the database.
     *
     * todo: The user has already been fetched from database once, this method fetches it again,
     *  which is probably excessive.
     * todo: Refactor: move id, name and password checks into user class.
     */
    boolean loginSelectedUser() {
        User that = this.session.get(User.class, this.getSelectedUserID());
        log.debug("Comparing passwords: '{}' VS '{}'", this.selectedUser.getId(), that.getId()); //debug line.

        if (this.selectedUser.getId().equals(that.getId())) {

            if (this.selectedUser.getName().equals(that.getName())) {

                if (this.selectedUser.getPassword().equals(that.getPassword())) {
                    this.authenticatedUser = this.selectedUser;

                    return true;
                }
            }
        }

        return false;
    }


    /**
     * set authenticatedUser to null.
     */
    void logoutSelectedUser() {
        this.authenticatedUser = null;
    }


    /**
     * Create a new user.
     *
     * @param userName          a String of the new user name
     * @param password          a string of the new user ID
     * @return boolean          true if user was created
     */
    boolean createUser(String userName, String password) {
        this.selectedUser = new User(userName, password);
        this.session.save(this.selectedUser);

        return true;
    }


    /**
     * Print the entire user database to stdout.
     *
     * 1) Databases can return different types for COUNT. Long is usually safest.
     * 2) Count('userID') does not count NULL in Column, whereas COUNT(*) would do.
     *
     * todo: Consider fetching objects directly by their ID instead of in the for loop.
     *  Possible perhaps by storing valid userIds in a List and iterating over that for retrieving.
     *  Alternatively select all userNames from User table and just print them.
     *
     */
    void printUserList() {

        Query query = this.session.createSQLQuery("SELECT Count('userID') FROM user");
        Long userCount = (Long) query.getSingleResult();

        for (int id = 0; id < userCount.intValue(); id++) {

            User currentUser = this.session.get(User.class, id);

            try {

                if (currentUser != null) {
                    System.out.printf("\n%s - UserName: %s", id, currentUser.getName());
                }

            } catch (IndexOutOfBoundsException e) {
               log.warn("Index out of bounds: {}", e);
            }
        }
    }
}

