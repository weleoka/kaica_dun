package kaica_dun_system;

import kaica_dun.dao.*;
import kaica_dun.entities.*;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.MenuException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


/**
 * A class which handles the main entry, setup and leaving of a dungeon game.
 *
 * This class will maintain a log of progress
 *
 */
@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    private static final Logger log = LogManager.getLogger();

    Avatar avatar;
    Dungeon dungeon;
    Room room;

    @Autowired
    UserInterface ui;

    @Autowired
    AvatarInterface ai;

    @Autowired
    DungeonInterface di;

    @Autowired
    GameServiceImpl gsi;

    @Autowired
    RoomInterfaceCustom ric;

    @Autowired
    RoomInterface ri;

    @Autowired
    ActionMenuRoom amr;

    @Autowired
    MenuLoggedIn mli;

    @Autowired
    EntityManager em;






    /**
     * Prime the game world and get it ready.
     *
     * This could be the constructor as well but with Autowired it's better to use method injection in this case.
     *
     * It sets values for avatar and dungeon. Also the important aspect of identifying the
     * first room in a dungeon is done here.
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
    }

    /**
     * Drops the avatar inte the first room of a dungeon.
     */
    public void playNew() {
        Room firstRoom = gsi.findFirstRoomInDungeon(dungeon);

        log.debug("Dropping avatar into room (id: {}) -> good luck!.", firstRoom.getId());
        avatar.setCurrRoom(firstRoom);
        ai.save(avatar);

        //UiString.printGameIntro();

        play();
    }

    /**
     * Resume a game that has been paused.
     *
     * todo: implement some feedback for this..
     *
     */
    public void resume() {

        try {
            Room room = avatar.getCurrRoom();

        } catch (NullPointerException e) {
            log.warn("Trying to resume a game that does not exist. No Current room is set for Avatar.");
            playNew();
        }
        log.debug("Resuming game.");
        play();
    }

    /**
     * Restart the current game with the same parameters.
     * Currently it is a random re-generation, as original parameters were not saved.
     * todo: implement original values for all rooms etc for real re-start.
     */
    public void restart() {
        log.debug("Restarted the game. (Done by calling playNew().");
        playNew();
    }





    /**
     * The method for initialising game loop and handling
     * exception bubbling thrown in nested loops.
     *
     * todo: move the string reports out to UiStrings.
     */
    @Override
    public void play() {
        log.debug("Starting game loop.");

        mainGameLoop:
        while(true) {
            this.room = avatar.getCurrRoom();

            if (em.isJoinedToTransaction()) {
                em.refresh(this.room);
                em.flush(); // Not sure but testing anyhow.
            }

            try {
                System.out.println(buildStateInfo());
                amr.display();

            } catch (MenuException e) {
                log.debug("Quit the game. Breaking mainGameLoop.");
                System.out.println("Quit the current game.");
                break mainGameLoop;

            } catch (GameOverException e) {
                log.debug("Game over. Breaking mainGameLoop.");
                System.out.println("Your avatar has died in battle...");
                break mainGameLoop;

            } catch (GameWonException e) {
                log.debug("The avatar has prevailed and won the game! Breaking mainGameLoop.");
                System.out.println("You have won the game and completed the dungeon!");
                break mainGameLoop;
            }

        }
    }





    /**
     * Method to collect and output to user interesting information.
     *
     * todo: move the strings to UiStrings for consistency and language support.
     * todo: split the method up.
     */
    private String buildStateInfo() {
        StringBuilder str;
        String monstersInTheRoom;
        String exitsFromTheRoom;
        String itemsInTheRoom;


        // Building monsters and foes
        str = new StringBuilder();
        List<Monster> monsters = room.getMonsters();

        if (monsters.size() > 0 ) {
            str.append(String.format("There are %s dangers in the room:", monsters.size()));
            for (int i = 1; i < monsters.size() + 1; i++) {
                Monster monster = monsters.get(i - 1);
                str.append(String.format("\n%s with health %s.", monster.getType(), monster.getCurrHealth()));
            }
            monstersInTheRoom = str.toString();
        } else {
            monstersInTheRoom = UiString.noMonstersVisible;
        }


        // Building directions and exits
        str = new StringBuilder();
        List<Direction> exits = room.getExits();
        str.append(String.format("There are %s passages leading from the chamber: ", exits.size()));
        for (Direction direction: exits) {
            str.append(String.format("%s, ", direction.getName()));
        }
        exitsFromTheRoom = str.toString();


        // Combine it all to make sense.
        str = new StringBuilder();
        str.append(
                String.format("\n%s health: %s", avatar.getName(), avatar.getCurrHealth()) +
                String.format("\n%s", monstersInTheRoom) +
                String.format("\n%s", exitsFromTheRoom) +
                String.format("\n%s", UiString.randomSound()) +
                String.format("\n%s", UiString.randomVisual())
        );

        return str.toString();
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
