package kaica_dun_system;


import kaica_dun.entities.Avatar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameControl {
    // Singleton
    private static GameControl ourInstance = new GameControl();
    public static GameControl getInstance() {
        return ourInstance;
    }
    private GameControl() {}

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private Avatar avatar = null;


    /**
     * Uses native SQL.
     *
     * todo: sort out compiler warning about unchecked assignment.
     * @param user a User instance
     * @return
     */
    public List<Avatar> fetchAvatarByUser(User user) {
        log.debug("Searching for all Avatars belonging to {}.", user.getName());

        //MainDao avatarDao = new DaoFactory().getMainDao(Avatar.class);

 /*       Query query = session.createSQLQuery(
                "SELECT * FROM fighter avatar WHERE avatar.userID LIKE :userID")
                .addEntity(Avatar.class)
                .setParameter("userID", user.getId());
        List<Avatar> avatarList = query.getResultList(); // List<User> result = query.getResultList();
        session.getTransaction().commit();
*/
        //log.debug("A List of avatars was fetched: {}", avatarList);

        return null;//avatarList;
    }


    /**
     *
     * @param arr a String[] with name and description
     * @param user a User instance
     * @return boolean if success
     */
    public boolean createNewAvatar(String[] arr, User user) {
        Avatar avatar = new Avatar(arr[0], arr[1], user);
        //MainDao dao = new DaoFactory().getMainDao(Avatar.class);

        //dao.save(avatar);
        return true;
    }

    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }
}
