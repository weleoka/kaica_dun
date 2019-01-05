package kaica_dun_system;

import kaica_dun.dao.*;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun.util.KaicaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    private static final Logger log = LogManager.getLogger();

    Avatar avatar;
    Dungeon dungeon;
    Room currentRoom;

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

    @Autowired
    MenuLoggedIn mli;


    /**
     * Prime the game world and get it ready.
     *
     * This could be the constructor as well but with Autowired it's better to use method injection in this case.
     *
     * It sets values for user, avatar and dungeon. Also the important aspect of identifying the
     * first room in a dungeon is worked at.
     *
     * @param avatar
     * @param dungeon
     */
    public void prime(Avatar avatar, Dungeon dungeon) {
        log.debug("Priming Action Engine environment...");
        log.debug("User: {}, Avatar: '{}', Dungeon: {}", dungeon.getUser().getName(), avatar.getName(), dungeon.getDungeonId());

        this.avatar = avatar;
        this.dungeon = dungeon;

        log.debug("Setting the Avatar pointer to the dungeon.");
        avatar.setCurrDungeon(dungeon);
        log.debug("Committing avatar with dungeon pointers to db");
        as.save(avatar);

        Room firstRoom = null;
        Long firstRoomId = ric.findFirstRoomInDungeon(dungeon);

        log.debug("Fetching first room (id: {}) of the dungeon.", firstRoomId);
        Optional result = ri.findById(firstRoomId);
        if (result.isPresent()) {
            firstRoom = (Room) result.get();
        }

        log.debug("Dropping avatar into room (id: {}) -> good luck!.", firstRoom.getId());
        this.avatar.setCurrRoom(firstRoom);
    }

    /**
     * The method for initialising game loop and handling
     * exception bubbling thrown in nested loops.
     *
     */
    public void play() {
        log.debug("Starting game loop.");

        mainGameLoop:
        while(true) {

            try {
                amr.display();

            } catch (KaicaException e) {
                log.debug("Quit the game. Breaking mainGameLoop.");
                break mainGameLoop;
            }
        }
        mli.display();

    }

    /**
     * Resume a game that has been paused.
     *
     */
    public void resume() {
        log.debug("Resuming game.");
        play();
    }

    /**
     * Restart the current game with the same parameters.
     * Currently it is a random re-generation, as original parameters were not saved.
     * todo: implement original values for all rooms etc for real re-start.
     */
    public void restart() {
        log.debug("Restarted the game.");
        play();
    }







    // ********************** Accessor Methods ********************** //

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

    public Room getAvatarCurrentRoom() {
        return getAvatar().getCurrRoom();
    }

}
