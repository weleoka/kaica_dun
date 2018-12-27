package kaica_dun_system;

import kaica_dun.entities.Avatar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GameControl {
    // Singleton
    private static GameControl ourInstance = new GameControl();
    public static GameControl getInstance() {
        return ourInstance;
    }
    private GameControl() {}

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private Session session = SessionUtil.getSession();
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

        Query query = this.session.createSQLQuery(
                "SELECT * FROM fighter avatar WHERE avatar.userID LIKE :userID")
                .addEntity(Avatar.class)
                .setParameter("userID", user.getId());
        List<Avatar> avatarList = query.getResultList(); // List<User> result = query.getResultList();

        log.debug("A List of avatars was fetched: {}", avatarList);

        return avatarList;
    }

    /**
     *
     * @param arr a String[] with name and description
     * @param user a User instance
     * @return boolean if success
     */
    public boolean createNewAvatar(String[] arr, User user) {
        Avatar avatar = new Avatar(arr[0], arr[1], user);
        this.session.save(avatar);
        return true;
    }

    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }
}
