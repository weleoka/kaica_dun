package kaica_dun_system;

import kaica_dun_system.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Map;

/**
 * Currently handling details of reading and writing USer to a string for
 * saving in the database. The array is as follows:
 *
 * userArr[0] is the userName
 * userArr[1] is the userID
 * userArr[2] is the status
 *
 * todo: move the to/from database logic to out into User class.
 * todo: pass the User instance to callers?
 */
public class UserControl {
    // Fields declared
    private User selectedUser;  // user object that is subject to operations.
    private User authenticatedUser; // holds a reference to the user object if isAuthenticated.
    Session session = SessionUtil.getSession();

    // Singleton
    private static UserControl ourInstance = new UserControl();

    public static UserControl getInstance() {
        return ourInstance;
    }

    // Constructor
    private UserControl() {
    }


    /**
     * Read from storage and find a user by user name.
     * <p>
     * If found then set selectedUser to new instance with attributes set.
     *
     * @param userName a String of the name too look for
     */
    public boolean selectUserByName(String userName) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin(); //You don’t need to start the transaction after you’ve created your EntityManager. But please be aware, that Hibernate will start and commit a transaction for each database operation if there is no active transaction. In most situations, it’s better to start only one transaction. That reduces the load on the database and assures consistent reads for the duration of your transaction.

        // Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<User> criteria = builder.createQuery(User.class);


        TypedQuery<User> query = em.createQuery("SELECT c FROM user c WHERE user_name = :p", User.class);

        List<User> results = query.getResultList();

        session.close();

        em.getTransaction().commit();
        em.close();


        return true;

      /*  User user = session.get(User.class, userName);

        for (String[] userArr : userList) {

            if (userName.equalsIgnoreCase(userArr[0])) {
                this.selectedUser = new User(userArr);

                return true;
            }
        }

        return false;*/
    }

    /**
     * Read from storage and find a user by user id.
     * <p>
     * If found in DB then set selectedUser to relevant instance.
     *
     * @param userID a String of the users id to look for
     */
    public boolean selectUserByUserID(String userID) {
        User user = session.get(User.class, userID);

        if (user != null) {
            this.selectedUser = user;

            return true;
        }

        return false;
    }


    /**
     * Update the user in DB
     */
    public void updateUser() {
/*        boolean userFound = false;
        this.userDB.readCSVFull();
        String tmpName = this.selectedUser.getName();
        List<String[]> userList = this.userDB.readCSVFull();

        for (int i = 0; i < userList.size(); i++)
        {
            String[] userArr = userList.get(i);

            if (tmpName.equalsIgnoreCase(userArr[0]))
            {
                userArr = this.selectedUser.toArray();
                userList.set(i, userArr);
                userFound = true;
                break;
            }
        }

        if (userFound)
        {
            System.out.println("DEBUG: User found and updated.");
        }*/
    }

    /*    *//**
     * Print the entire user database to stdout.
     *//*
    public void printUserList()
    {
        List<String[]> userList = this.userDB.readCSVFull();

        for ( String[] user : userList)
        {

            for (String item : user)
            {
                System.out.println(item);
            }
        }
        System.out.println("==========================");
    }*/


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


    /**
     * setAuthenticated
     * <p>
     * The selectedUser variable should already be set before this method is called.
     * This method implements a check that the supplied UserID matches the stored selectedUser.id.
     */
    public boolean loginSelectedUser(String userID) {
        //System.out.printf("'%s' VS '%s'", userID, this.selectedUser.getSelectedUserID()); //debug line.
        if (userID.equals(this.selectedUser.getId())) {
            this.authenticatedUser = this.selectedUser;

            return true;
        }

        return false;
    }

    /**
     * set authenticatedUser to null.
     */
    public void logoutSelectedUser() {
        this.authenticatedUser = null;
    }


    /**
     * Create a new user.
     * <p>
     * todo: implement checks to make sure no duplicate users can be created.
     *
     * @param userName a String of the new user name
     * @param userID   a string of the new user ID
     * @return boolean              true if user was created
     */
    public boolean createUser(String userName, String userID) {
        this.selectedUser = new User(userName, userID);


        return true;

    }
}

