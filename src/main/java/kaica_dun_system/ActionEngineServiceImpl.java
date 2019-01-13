package kaica_dun_system;

import kaica_dun.config.KaicaDunCfg;
import kaica_dun.entities.*;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import kaica_dun_system.menus.ActionMenuRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


/**
 * A class which handles the main entry, setup and leaving of a dungeon game.
 *
 * The main points of interest for this service is:
 * - Monsters
 * - Directions
 * ... - Items?
 *
 *
 * @author Kai Weeks
 *
 */
@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    private static final Logger log = LogManager.getLogger();

    private Set<Monster> monsters;
    private Set<Direction> directions;

    @Autowired
    KaicaDunCfg kcfg;

    @Autowired
    UserServiceImpl usi;

    @Autowired
    GameServiceImpl gsi;

    @Autowired
    ActionMenuRoom amr;

    @Autowired
    MovementServiceImpl msi;


    /**
     * Creates a new dungeon and calls the operations to activate it.
     */
    public void playNew(Avatar avatar) throws MenuException {
        Dungeon dungeon = null;
        if (avatar.getCurrDungeon() == null) {
            dungeon = gsi.makeStaticDungeon();
            gsi.resetAvatar();
        } else {
            dungeon = avatar.getCurrDungeon();
        }
        gsi.setDungeon(dungeon);
        msi.enterDungeon(avatar, dungeon);
        printDebugInfo("New game: ");
        //UiString.printLoadingIntro();
        //UiString.printGameIntro();
        play(avatar);
    }

    /**
     * For testing, relies on user avatar and dungeon to be set already.
     */
    public void playLoad(Avatar avatar) throws MenuException {
        play(avatar);
    }

    /**
     * Resume a game that has been paused.
     */
    public void resume(Avatar avatar) throws MenuException {
        Room room = null;

        try {
            room = gsi.getAvatar().getCurrRoom();

        } catch (NullPointerException e) {
            log.warn("No Current room is set for Avatar. Can't resume game.");
            throw new MenuException("Quit the current game");
        }
        log.debug("Found an active room ({}) for avatar: {}", room.getId(), gsi.getAvatar().getName());
        printDebugInfo("Resume game: ");
        UiString.printLoadingIntro();
        play(avatar);
    }


    /**
     * Restart the current game with the same parameters.
     * Currently it is a random re-generation, as original parameters were not saved.
     * todo: implement original values for all rooms etc for real re-start.
     */
    public void restart(Avatar avatar) throws MenuException {
        printDebugInfo("Restart game: ");
        gsi.resetAvatar();
        msi.enterDungeon(avatar, avatar.getCurrDungeon());
        if (!kcfg.debug) {
            UiString.printLoadingIntro();
            UiString.printGameIntro();
        }
        play(avatar);
    }


    /**
     * The method for initialising game loop and handling
     * exception bubbling thrown in nested loops.
     *
     * todo: move the string reports out to UiStrings.
     */
    @Override
    public void play(Avatar avatar) throws MenuException {
        log.debug("Starting game loop.");

        mainGameLoop:
        while(true) {
            //this.room = avatar.getCurrRoom();
            //TODO Think about how we want to handle having exited the dungeon.
            if (getCurrentRoom() != null) {
                monsters = getMonstersCurrentRoom();
                directions = getDirectionsCurrentRoom();
            } else { break mainGameLoop; }

            try {
                System.out.println(buildStateInfo());
                amr.display(avatar);

            } catch (MenuException e) {
                log.debug("Quit the game. Breaking mainGameLoop.");
                System.out.println("Quit the current game.");
                break mainGameLoop;

            } catch (GameOverException e) {
                log.debug("Game over. Breaking mainGameLoop.");
                System.out.println("Your avatar has died in battle...");
                gsi.resetAvatar();
                Util.sleeper(2400);
                throw new MenuException("Quit in-game menu due to game over.");

            } catch (GameWonException e) {
                log.debug("Your avatar has prevailed, and you have won the game! Breaking mainGameLoop.");
                System.out.println("You have won the game and completed the dungeon!");
                Util.sleeper(2400);
                throw new MenuException("Quit in-game menu due to win.");
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
        //String describablesInTheRoom;

        // Building monsters and foes
        str = new StringBuilder();

        if (monsters.size() > 0 ) {
            str.append(String.format("There are %s dangers in the room:", monsters.size()));

            for (Monster monster : monsters) {
                str.append(String.format("\n%s with health %s.", monster.getType(), monster.getCurrHealth()));
            }
            monstersInTheRoom = str.toString();

            } else {
                monstersInTheRoom = UiString.noMonstersVisible;
        }


        // Building directions and directions
        str = new StringBuilder();

        if (directions.size() > 0 ) {
            str.append(String.format("There are %s passages leading from the chamber: ", directions.size()));

            for (Direction direction : directions) {
                str.append(String.format("%s, ", direction.getName()));
            }
            exitsFromTheRoom = str.toString();

        } else {
            exitsFromTheRoom = UiString.noExitsFromTheRoom;
        }


        // Building describables
/*        str = new StringBuilder();

        if (describables.size() > 0) {
            str.append(String.format("There are %s thisng to look at in the room.", describables.size()));

            for (Describable describable : describables) {
                str.append(String.format("%s, ", describable.getDescription()));
            }
            describablesInTheRoom = str.toString();
        } else {
            describablesInTheRoom = UiString.noDescribablesVisible;
        }*/


        // Combine it all to make sense.
        str = new StringBuilder();
        str.append(
                String.format("\n%s health: %s", gsi.getAvatar().getName(), gsi.getAvatar().getCurrHealth()) +
                String.format("\n%s", monstersInTheRoom) +
                String.format("\n%s", exitsFromTheRoom) +
                //String.format("\n%s", describablesInTheRoom) +
                String.format("\n%s", UiString.randomSound()) +
                String.format("\n%s", UiString.randomVisual())
        );

        return str.toString();
    }



    // ********************** Accessor Methods ********************** //

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public Set<Direction> getDirections() {
        return directions;
    }


    public Set<Monster> getMonstersCurrentRoom() {
        return gsi.getAvatarCurrentRoom().getMonsters();
    }

    public Set<Direction> getDirectionsCurrentRoom() {
        return gsi.getAvatarCurrentRoom().getDirections();
    }

    public Room getCurrentRoom() {
        return gsi.getAvatarCurrentRoom();
    }



    // ********************** Helper Methods ********************** //

    public void printDebugInfo(String extra) {
        log.debug("{} (User: {}, Avatar: '{}', Dungeon: {})",
                extra,
                //gsi.getDungeon().getUser().getName(),
                usi.getAuthenticatedUser().getName(),
                gsi.getAvatar().getName(),
                gsi.getDungeon().getDungeonId());
    }
}
