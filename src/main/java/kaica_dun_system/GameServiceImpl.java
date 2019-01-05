package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import kaica_dun.resources.makeAvatar;
import kaica_dun.resources.makeStaticDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
    private Avatar avatar;
    private Dungeon dungeon;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private DungeonInterface dungeonInterface;

    @Autowired
    ActionEngineServiceImpl aesi;

    @Autowired
    private EntityManager entityManager;





    // ********************** Dungeon Methods ********************** //

    /**
     * getCurrent generated dungeon.
     *
     * So the idea would always be to get dungeon by id. That way it is possible to get a saved game.
     *
     * todo: implement fetching dungeon from database.
     *  Need also to think about how currentRoom for avatar in that particular dungeon is saved.
     *
     * @return
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }


    /**
     * Get a new generated dungeon
     *
     * @return
     */
    public Dungeon getNewDungeon(User user) {
        return setDungeon(user);
    }


    /**
     * Create a dungeon for a user.
     *
     * todo: it still needs input about the avatar. In fact a dungeon should be owned
     *   by an avatar and not the user really.
     *
     * @param user
     * @return
     */
    public Dungeon setDungeon(User user) {
        log.debug("Creating static dungeon for user: {}", user.getName());
        makeStaticDungeon msd = new makeStaticDungeon(user);
        dungeon = msd.buildDungeon();

        log.debug("Adding dungeon to {}s list of dungeons.", user.getName());
        user.addDungeon(dungeon);

        userInterface.save(user);
        dungeonInterface.save(dungeon);
        this.dungeon = dungeon;

        return dungeon;
    }



    // ********************** Avatar Methods ********************** //

    /**
     * get current selected avatar.
     * @return
     */
    public Avatar getAvatar() {
        return this.avatar;
    }

    /**
     * Sets the Game service selected avatar.
     * @param avatar
     */
    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }





    // ********************** Persistence Methods ********************** //

    /**
     * Uses native SQL.
     *
     * todo: sort out compiler warning about unchecked assignment.
     * @param user a User instance
     * @return a list of avatars
     */
    public List<Avatar> fetchAvatarByUser (User user) {
        log.debug("Searching for all Avatars belonging to {}.", user.getName());

/*
        Query query = entityManager.createQuery(
                //"SELECT fighter FROM Fighter fighter WHERE Fighter.userID LIKE :userId")
                "SELECT Avatar FROM Avatar avatar WHERE avatar.userID = :userId")
                //.addEntity(Avatar.class)
                .setParameter("userId", user.getId());
        List<Avatar> avatarList = query.getResultList();
*/
        // todo: adapt this method to the JPA criteria API.... It is hard with the inheritance strategy and
        //  having to reference columns by field variable names and not column names.
        TypedQuery<Avatar> query = this.entityManager.createNamedQuery("Avatar.findByUserID", Avatar.class);
        //Query query = this.entityManager.createNativeQuery("SELECT * FROM Fighter f WHERE f.userId = ?", Avatar.class);


        query.setParameter("userInstance", user); // Named
        //query.setParameter(1, user.getId()); // Native
        List<Avatar> results = query.getResultList();
        log.debug("A List of {} avatars was fetched.", results.size());

        return results;
    }


    /**
     * @param user a User instance
     * @return boolean if success
     */
    public Avatar createStaticAvatar(User user) {
        Avatar avatar =  makeAvatar.make(user);
        avatarInterface.save(avatar);
        return avatar;
    }

    /**
     *
     * @param arr a String[] with name and description
     * @param user a User instance
     * @return boolean if success
     */
    public boolean createNewAvatar(String[] arr, User user) {
        Avatar avatar = new Avatar(arr[0], arr[1], user);

        avatarInterface.save(avatar);
        return true;
    }





    // ********************** Model logic access ********************** //

    /**
     * Find all the avatars belonging to a certain user.
     * If boolean value is set then print the results to stdout.
     *
     * @param user
     * @param stdout
     * @return
     */
    public String printAvatarListByUser(User user, boolean stdout) {
        String stringOutput = "";
        String row = "";
        List<Avatar> avatarList = fetchAvatarByUser(user);

        for (int i = 0; i < avatarList.size(); i++) {
            Avatar tmpAvatar = avatarList.get(i);
            row = String.format("\n[%s] - %s", i + 1, tmpAvatar.getName()); // plus 1 for readability

            if (stdout) {
                System.out.println(row);
            }
            stringOutput += row;
        }

        return stringOutput;
    }
}
