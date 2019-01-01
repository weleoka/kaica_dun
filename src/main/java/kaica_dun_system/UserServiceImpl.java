package kaica_dun_system;


import kaica_dun.dao.DaoFactory;
import kaica_dun.dao.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class UserServiceImpl implements UserService {

    // Fields declared
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    // User management
    private User selectedUser;  // user object that is subject to operations.
    private User authenticatedUser; // holds a reference to the user object if isAuthenticated.

    /* // Removedconstructor injection in favour of property injection.
    @Autowired
    public UserServiceImpl() { }

    // Removed constructor so now
       this.userDao = new DaoFactory().getUserDao();
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

*/


    // ********************** Persistence Methods ********************** //
    /**
     * Create a new user.
     *
     * @param user a User instance
     * @return boolean          true if user was created
     */
    @Transactional
    public Long createUser(User user) { // Change to package private after testing.
        log.debug("Creating user '{}'.", user.getName());

        try {
            User nUser = userDao.create(user);

            if (nUser.getId() != null) {
                log.debug("Created new user with ID: '{}'.", user.getId());
                this.selectedUser = user;

                return nUser.getId();
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
    @Transactional(readOnly = true)
    public User findById(Long userId) { // Change to package private after testing.
        log.debug("Searching for user with ID: {}.", userId);
        User user = new User();

        try {
            userDao.read(userId);

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
    @Transactional(readOnly = false)
    public User findByName(String userName) { // Change to package private after testing.
        log.debug("Searching for user '{}' by name.", userName);
        User user = new User();

        try {

            userDao.findByName(userName);

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
    @Transactional(readOnly = false)
    public List<User> findAll() {
        log.debug("Fetch all user objects");

        try {

            return this.userDao.findAll();

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
    public boolean checkNewUserName(String userName) {

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
    public boolean isAuthenticatedUser() {

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
    public Long getSelectedUserID() {
        return this.selectedUser.getId();
    }

    public User getSelectedUser() {
        return this.selectedUser;
    }




    /**
     * setAuthenticated
     *
     * The selectedUser variable should already be set before this method is called.
     * This method implements a check that the selectedUser in the UserServiceImpl is indeed the same
     * object as is stored in the database.
     *
     * todo: The user has already been fetched from database once, this method fetches it again,
     *  which is probably excessive.
     * todo: Refactor: move id, name and password checks into user class.
     */
    public boolean loginSelectedUser() {

        User that = userDao.read(this.getSelectedUserID());

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
    public void logoutSelectedUser() {
        this.authenticatedUser = null;
    }






}

