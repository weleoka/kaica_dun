package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@EnableTransactionManagement
public class GameServiceImpl implements GameService {

    // Singleton
    private static GameServiceImpl ourInstance = new GameServiceImpl();
    public static GameServiceImpl getInstance() {
        return ourInstance;
    }
    private GameServiceImpl() {}

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private Avatar avatar = null;

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private EntityManager entityManager;
    /**
     * Uses native SQL.
     *
     * todo: sort out compiler warning about unchecked assignment.
     * @param user a User instance
     * @return
     */
    public List<Avatar> fetchAvatarByUser(User user) {
        log.debug("Searching for all Avatars belonging to {}.", user.getName());

        Query query = entityManager.createQuery(
                "SELECT avatar FROM Fighter avatar WHERE avatar.userID LIKE :userID")
                //.addEntity(Avatar.class)
                .setParameter("userID", user.getId());
        List<Avatar> avatarList = query.getResultList();

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
        //MainDao dao = new DaoFactory().getMainDao(Avatar.class);

        //dao.save(avatar);
        return true;
    }

    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }
}
