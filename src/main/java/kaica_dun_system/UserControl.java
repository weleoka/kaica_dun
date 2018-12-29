package kaica_dun_system;

import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.UserDaoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import java.util.List;


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

    // Singleton
    private static UserControl ourInstance = new UserControl();
    public static UserControl getInstance() {
        return ourInstance;
    } // Change to package private after testing.
    private UserControl() {}

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private static final SessionUtil SESSIONUTIL = SessionUtil.getInstance();
    private Session session = null;

    // DAO
    private DaoFactory daoFactory = DaoFactory.instance(DaoFactory.HIBERNATE);

    // User management
    private User selectedUser;  // user object that is subject to operations.
    private User authenticatedUser; // holds a reference to the user object if isAuthenticated.


    /**
     * Create a new user.
     *
     * @param user a User instance
     * @return boolean          true if user was created
     */
    public Long create(User user) { // Change to package private after testing.
        log.debug("Creating user '{}'.", user.getName());

        try {
            UserDaoInterface udao = daoFactory.getUserDao();
            Long userId = udao.create(user);

            if (userId != null) {
                log.debug("Created new user with ID: '{}'.", user.getId());
                this.selectedUser = user;

                return userId;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        log.debug("Could not create new user.");

        return null;
    }

    /**
     * Read single from storage and find a user by user id.
     * If found in DB then set selectedUser to relevant instance.
     *
     * @param userId a Long of the users id to look for
     */
    public User findById(Long userId) { // Change to package private after testing.
        log.debug("Searching for user with ID: {}.", userId);
        User user = new User();

        try {
            UserDaoInterface udao = daoFactory.getUserDao();
            user = udao.read(userId);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        if (user.getId() != null) {
            log.debug("Found user with ID: '{}'", user.getId());
            this.selectedUser = user;

        } else {
            log.debug("Found no user with ID: '{}'", user.getId());
        }

        return user;
    }


    /**
     * Read single from storage and find a user by user name.
     *
     * https://stackoverflow.com/questions/25440284/fetch-database-records-using-hibernate-without-using-primary-key
     * https://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
     *
     * todo: Native SQL makes hibernate database implementation dependent.
     * todo: Calls Query#getSingleResult and that does not take into account multiple users with same name.
     *
     * @param userName a String of the name too look for
     */
    public User findByName(String userName) { // Change to package private after testing.
        log.debug("Searching for user '{}' by name.", userName);
        User user = new User();

        try {
            UserDaoInterface udao = daoFactory.getUserDao();
            user = udao.findByName(userName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        if (user.getId() != null) {
            log.debug("Found user with name: '{}'", user.getName());
            this.selectedUser = user;

        } else {
            log.debug("Found no user with name: '{}'", user.getName());
        }

        return user;
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
    public List<User> findAll() {
        log.debug("Fetch all user objects");

        try {
            UserDaoInterface udao = daoFactory.getUserDao();
            return udao.findAll();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }



    // ********************** Checking Methods ********************** //

    /**
     * Check if the username is available
     */
    boolean checkNewUserName(String userName) {

        if (findByName(userName) != null) {
            this.selectedUser = null;

            return false;
        }

        return true;
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
        session = SESSIONUTIL.getSession();
        User that = this.session.get(User.class, this.getSelectedUserID());
        session.getTransaction().commit();

        log.debug("Comparing passwords: '{}' VS '{}'", this.selectedUser.getPassword(), that.getPassword()); //debug line.

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






}

