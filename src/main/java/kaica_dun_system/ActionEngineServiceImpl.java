package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    private static final Logger log = LogManager.getLogger();

    User user;
    Avatar avatar;
    Dungeon dungeon;
    Room selectedRoom;

    @Autowired
    UserInterface ui;

    @Autowired
    AvatarInterface as;

    @Autowired
    DungeonInterface di;

    @Autowired
    RoomInterface ri;

    @Autowired
    ActionMenuRoom amr;


    /**
     * Prime the game world and get it ready.
     *
     * This could be the constructor as well but with Autowired it's better to use method injection in this case.
     *
     * It sets values for user, avatar and dungeon. Also the important aspect of identifying the
     * first room in a dungeon is worked at.
     *
     * todo: Currently the logic for finding the first room in a dungeon is a bit unsatisfying.
     *
     * @param user
     * @param avatar
     * @param dungeon
     */
    public void prime(User user, Avatar avatar, Dungeon dungeon) {
        log.debug("Primed the Action Engine environment.");
        this.user = user;
        this.avatar = avatar;
        this.dungeon = dungeon;
        Room firstRoom = null;

        List<Room> rooms = dungeon.getRooms();
        if (rooms.size() == 0) {
            log.warn("No rooms in the dungeon!");
        }

        Long smallestLong = 9999L;
        Long tmp = 10000L;

        for (Room room : rooms) {
            log.debug(room);
            try {
                tmp = room.getId();
            } catch (NullPointerException e) {
                log.warn("Invalid room with null ID.");
                e.printStackTrace();
            }
            if (tmp < smallestLong);
                smallestLong = tmp;
        }

        log.debug("The room with the lowest ID in the dungeon is: {}.", smallestLong);
        Optional<Room> dbRoom = ri.findById(smallestLong);

        if (dbRoom.isPresent()) {
            firstRoom = dbRoom.get();
        }
        if (firstRoom != null) {
            this.avatar.setCurrRoom(firstRoom);
        }

    }


    // Start playing
    public void play() {
        log.debug("Started new game.");
        amr.display();//this.user, this.avatar, this.dungeon);
    }


    // Resume playing
    public void resume() {
        log.debug("Resuming game.");
    }

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    public void restart() {
        log.debug("Restarted the game.");
    }







    // ********************** Accessor Methods ********************** //
    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    private void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    private void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Room getRoom() {
        return getAvatar().getCurrRoom();
    }

    private void setRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
}
