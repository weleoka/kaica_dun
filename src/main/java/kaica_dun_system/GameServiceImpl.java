package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.resources.makeAvatar;
import kaica_dun.resources.makeStaticDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    private AvatarInterface avatarInterface;

    @Autowired
    private DungeonInterface dungeonInterface;

    @Autowired
    private EntityManager entityManager;


    public Dungeon createDungeon(User user) {

        // @Kai TODO move this somewhere?
        avatar = makeAvatar.make(user);
        avatarInterface.save(avatar);

        if (this.avatar != null) {
            log.debug("Creating static dungeon for user: {}", user.getName());
            makeStaticDungeon msd = new makeStaticDungeon(user);
            dungeon = msd.buildDungeon();
            dungeonInterface.save(dungeon);
        }

        return this.dungeon;
    }


    public void playGame() {
        //Game.engine(this.avatar, this.dungeon)
    }





    // ********************** Persistence Methods ********************** //

    /**
     * Uses native SQL.
     *
     * todo: sort out compiler warning about unchecked assignment.
     * @param user a User instance
     * @return
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

    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }
}
