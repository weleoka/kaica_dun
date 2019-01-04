package kaica_dun_system;

import kaica_dun.dao.*;
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
    RoomInterfaceCustom ric;

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
        log.debug("Priming Action Engine environment...");
        this.user = user;
        this.avatar = avatar;
        this.dungeon = dungeon;
        log.debug("User: '{}', Avatar: '{}', Dungeon:'{}'", user.getName(), avatar.getName(), dungeon.getDungeonId());
        Room firstRoom = null;
        Long firstRoomId = ric.findFirstRoomInDungeon(dungeon.getDungeonId());
        log.debug("The room with the lowest ID in the dungeon is: {}.", firstRoomId);

        Optional result = ri.findById(firstRoomId);

        if (result.isPresent()) {
            firstRoom = (Room) result.get();
        }

        log.debug("Fetched the first room (id: {}) from db and applying to Avatar.", firstRoom.getId());
        this.avatar.setCurrRoom(firstRoom);
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
