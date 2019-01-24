package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.resources.AvatarFactory;
import kaica_dun.resources.StaticDungeonFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;


/**
 * Class providing useful functions for working with different objects relevant to the game.
 *
 * The main points of interest for this service is:
 * - Avatar
 * - Dungeon
 *
 * If there is functionality for generating user output and game world sugar
 * keep that in the class called UiString.
 *
 * @author Kai Weeks
 *
 */
@Service
@EnableTransactionManagement
public class GameServiceImpl implements GameService {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private AvatarFactory avatarFactory;

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private DungeonInterface dungeonInterface;

    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager; // todo: see notes on fetchAvatarByUser todo's





    // ********************** Dungeon Methods ********************** //


    /**
     * Create a dungeon for a user.
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Dungeon makeStaticDungeon() {
        log.debug("Creating static dungeon");
        Dungeon dungeon = StaticDungeonFactory.buildDungeon();
        log.debug("Persisting static dungeon");
        dungeonInterface.save(dungeon);

        return dungeon;
    }





    // ********************** Avatar methods ********************** //

    /**
     * Uses native SQL.
     *
     * todo: sort out compiler warning about unchecked assignment.
     * todo: set this call out to the Repository interface and don't have EntityManager injected into this class
     * todo: adapt this method to the JPA criteria API.... It is hard with the inheritance strategy and
     *       having to reference columns by field variable names and not column names.
     *
     * @param user a User instance
     * @return a list of avatars
     */
    public List<Avatar> fetchAvatarByUser (User user) {
        log.debug("Searching for all Avatars belonging to {}.", user.getName());
        /*        Query query = entityManager.createQuery(
                //"SELECT fighter FROM Fighter fighter WHERE Fighter.userID LIKE :userId")
                "SELECT Avatar FROM Avatar avatar WHERE avatar.userID = :userId")
                //.addEntity(Avatar.class)
                .setParameter("userId", user.getId());
        List<Avatar> avatarList = query.getResultList();
*/
        TypedQuery<Avatar> query = entityManager.createNamedQuery("Avatar.findByUserID", Avatar.class); // Named
        //Query query = this.entityManager.createNativeQuery("SELECT * FROM Fighter f WHERE f.userId = ?", Avatar.class); // Native
        query.setParameter("userInstance", user); // Named
        //query.setParameter(1, user.getId()); // Native
        List<Avatar> results = query.getResultList();
        log.debug("A List of {} avatars was fetched.", results.size());
        return results;
    }


    /**
     * Creates an avatar and adds it to the Users list of avatars. Persists the User and the avatar.
     *
     * todo: not essential but if user had a lot of avatars a better method would be required for access
     *  to the set. See more at https://stackoverflow.com/a/16562663/3092830
     *
     * @param user a User instance
     * @return the avatar
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Avatar createStaticAvatar(User user) {
        Avatar avatar = avatarFactory.makeTestAvatar(user);
        avatarInterface.save(avatar);
        user.addAvatar(avatar);
        userInterface.save(user);
        log.debug("Saved a new avatar with id: {}", avatar.getId());
        return avatar;
    }


    /**
     * Creates a new avatar for the user.
     *
     * @param arr a String[] with name and description
     * @param user a User instance
     * @return boolean if success
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public boolean createNewAvatar(String[] arr, User user) {
        Avatar avatar = avatarFactory.make(arr[0], arr[1], user);
        avatarInterface.save(avatar);
        return true;
    }




    // ********************** Helper methods ********************** //

    /**
     * Find all the avatars belonging to a certain user.
     * If boolean value is set then print the results to stdout.
     *
     * @param user a user instance
     * @return str a string of information
     */
    public HashMap<Integer, String> buildAvatarOptions(User user) {
        HashMap<Integer, String> options = new HashMap<>();
        List<Avatar> avatars = fetchAvatarByUser(user);
        int i = 1;

        for (Avatar avatar : avatars) {
            options.put(i, String.format("\n[%s] - %s", i, avatar.getName()));
            i++;
        }

        return options;
    }
}
