package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.dao.RoomInterfaceCustom;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun.entities.RoomType;
import kaica_dun.resources.AvatarFactory;
import kaica_dun.resources.StaticDungeonFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private Avatar avatar;
    private Dungeon dungeon;

    @Autowired
    private AvatarFactory avatarFactory;

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private DungeonInterface dungeonInterface;

    @Autowired
    ActionEngineServiceImpl aesi;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoomInterfaceCustom ric;

    @Autowired
    private RoomInterface ri;




    // ********************** Game Methods ********************** //

    /**
     * Start playing a dungeon.
     */
    public void startDungeon() {
        Room firstRoom = fetchDungeonFirstRoom(dungeon);
        avatar.setCurrDungeon(dungeon);
        avatar.setCurrRoom(firstRoom);
        avatarInterface.save(avatar);
        log.debug("Dropping avatar into room (id: {}) -> good luck!.", firstRoom.getId());
    }






    // ********************** Dungeon Methods ********************** //


    /**
     * Create a dungeon for a user.
     *
     * todo: it still needs input about the avatar. In fact a dungeon should be owned
     *   by an avatar and not the user really.
     *
     * @return
     */
    @Transactional
    public Dungeon makeStaticDungeon() {
        log.debug("Creating static dungeon");
        dungeon = StaticDungeonFactory.buildDungeon();
        dungeonInterface.save(dungeon);

        return dungeon;
    }

    /**
     * Search for the room in the dungeon with the correct enum type.
     * This is the method where LAZY loading is used.
     *
     * todo: ensure that a dungeon only contains one single entry for certain enum types
     *  and modify the method for fetch single result.
     *
     * @param
     * @return
     */
    @Transactional
    public Room fetchDungeonFirstRoom() {
        List<UUID> results = ric.findRoomsInDungeonByEnum(dungeon, RoomType.FIRST01);
        UUID firstRoomId = results.get(0);

        Room firstRoom = null;

        log.debug("Fetching first room (id: {}) of the dungeon.", firstRoomId);
        Optional result = ri.findById(firstRoomId);
        if (result.isPresent()) {
            firstRoom = (Room) result.get();
        }

        return firstRoom;
    }

    /**
     * Find the first room if the dungeons rooms are EAGER loaded.
     *
     * @param dungeon
     * @return
     */
    public Room fetchDungeonFirstRoom(Dungeon dungeon) {
        Room room = null;
        for (Room r : dungeon.getRooms()){
            if(r.getRoomType() == RoomType.FIRST01) {
                room = r;
            }
        }
        return room;
    }





    // ********************** Avatar methods ********************** //

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
    @Transactional
    public Avatar createStaticAvatar(User user) {
        Avatar avatar =  avatarFactory.makeTestAvatar(user);
        avatarInterface.save(avatar);
        log.debug("Saved a new avatar with id: {}", avatar.getId());
        return avatar;
    }


    /**
     *
     * @param arr a String[] with name and description
     * @param user a User instance
     * @return boolean if success
     */
    @Transactional
    public boolean createNewAvatar(String[] arr, User user) {
        Avatar avatar = avatarFactory.make(arr[0], arr[1], user);
        avatarInterface.save(avatar);
        return true;
    }


    /**
     * Reset the health and current room of the avatar.
     */
    @Transactional
    public void resetAvatar() {
        avatar.setCurrHealth(avatar.getMaxHealth());
        avatar.setCurrRoom(null);
        avatarInterface.save(avatar);
    }


    /**
     * Save any changes changes to the avatar.
     * @return
     */
    @Transactional
    public void updateAvatar() {
        avatarInterface.save(avatar);
    }


    /**
     * Get the current room which an avatar is in.
     *
     * @return
     */
    public Room getAvatarCurrentRoom() {
        return getAvatar().getCurrRoom();
    }





    // ********************** Accessor methods ********************** //


    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        log.debug("An avatar(id:{}) has been set for username '{}'.", avatar.getId(), avatar.getUser().getName());
        this.avatar = avatar;
    }


    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }





    // ********************** Helper methods ********************** //

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
