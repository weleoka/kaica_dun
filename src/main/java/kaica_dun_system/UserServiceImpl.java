package kaica_dun_system;


import kaica_dun.dao.UserInterface;
import kaica_dun.dao.UserInterfaceCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
@EnableTransactionManagement
public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private UserInterfaceCustom userInterfaceCustom;




    // ********************** Persistence Methods ********************** //
    /**
     * Create a new user.
     *
     * @param nUser a User instance
     * @return boolean          true if user was created
     */
    @Transactional
    public Long createUser(User nUser) { // Change to package private after testing.
        log.debug("Creating user '{}'.", nUser.getName());

        try {
            User user = userInterface.save(nUser);

            if (user.getId() != null) {
                log.debug("Created new user with ID: '{}'.", user.getId());

                return user.getId();
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
    public User findUserById(Long userId) { // Change to package private after testing.
        log.debug("Searching for user with ID: {}.", userId);
        User user = null;

        try {
            Optional<User> dbUser = userInterface.findById(userId);
            if (dbUser.isPresent()) {
                user = dbUser.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        if (user != null) {
            log.debug("Found user with ID: '{}'", user.getId());
        } else {
            log.debug("Found no user with ID: '{}'", userId);
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
    public User findUserByName(String userName) { // Change to package private after testing.
        log.debug("Searching for user '{}' by name.", userName);
        User user;

        try {
            List<User> users = userInterfaceCustom.findByName(userName);
            user = users.get(0);

        } catch (IndexOutOfBoundsException e) {
            //e.printStackTrace();
            log.debug("Found no user with name: '{}'", userName);

            return null;
        }

        if (user.getId() != null) {
            log.debug("Found user with name: '{}'", userName);
            //this.selectedUser = user;
            return user;
        }

        return null;
    }


    /**
     * Fetch all the users in the database.
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

            return (List<User>) userInterface.findAll();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }




    // ********************** Checking Methods ********************** //

    /**
     * Check if the username is taken
     */
    public boolean checkNewUserName(String userName) {

        if (findUserByName(userName) != null) {

            return false;
        }

        return true;
    }




    // ********************** Authentication Methods ********************** //


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
    public boolean loginUser(User user, String password) {
        log.debug("Comparing passwords: '{}' VS '{}'", user.getPassword(), password); //debug line.

        if (user.getPassword().equals(password)) {

            return true;
        }

        return false;
    }






    // ********************** Development helper Methods ********************** //

    /**
     * Output the entire users list to stdout.
     */
    public void printUserList() {
        List<User> userList = (List<User>) userInterface.findAll();
        System.out.println("- - userlist - -");
        for (int id = 0; id < userList.size(); id++) {

            try {
                User currentUser = userList.get(id);
                System.out.printf("%s - UserName: %s\n", id + 1, currentUser.getName());

            } catch (IndexOutOfBoundsException e) {
                log.warn("Index out of bounds: {}", e);
            }
        }
        if (userList.size() == 0) {
            System.out.println("[empty]");
        }
        System.out.println();
    }

}

