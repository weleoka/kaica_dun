package kaica_dun_system;

import kaica_dun.config.KaicaDunCfg;
import kaica_dun.dao.AvatarInterface;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
@EnableTransactionManagement
public class ActionEngineServiceImpl implements ActionEngineService {
    private static final Logger log = LogManager.getLogger();

    private Set<Monster> monsters;
    private Set<Direction> directions;

    @Autowired
    private KaicaDunCfg kcfg;

    @Autowired
    private GameServiceImpl gsi;

    @Autowired
    private ActionMenuRoom amr;

    @Autowired
    private MovementServiceImpl msi;

    @Autowired
    private AvatarInterface avatarInterface;


    /**
     * Creates a new dungeon and calls the operations to activate it.
     */
    @Transactional
    public void playNew(Avatar avatar) throws MenuException {
        log.debug("New game: ");
        Dungeon dungeon = null;

        if (avatar.getCurrDungeon() == null) {
            dungeon = gsi.makeStaticDungeon();
            avatar.setCurrDungeon(dungeon);
            avatar.setHealthToMax();

        } else {
            dungeon = avatar.getCurrDungeon();
        }
        msi.enterDungeon(avatar);
        avatarInterface.save(avatar); // todo: calls save here msi.enterDungeon() does not;

        if (!kcfg.getDebug()) {
            UiString.printLoadingIntro();
            UiString.printGameIntro();
        }
        play(avatar);
    }


    /**
     * Resume a game that has been paused.
     */
    public void resume(Avatar avatar) throws MenuException {
        log.debug("Resume game: ");
        Room room = null;

        try {
            room = avatar.getCurrRoom();

        } catch (NullPointerException e) {
            log.warn("No Current room is set for Avatar. Can't resume game.");
            throw new MenuException("Quit the current game");
        }
        log.debug("Found an active room ({}) for avatar: {}", room.getId(), avatar.getName());

        if (!kcfg.getDebug()) {
            UiString.printLoadingIntro();
            play(avatar);
        }
    }


    /**
     * Restart the current game with the same parameters.
     * Currently it is a random re-generation, as original parameters were not saved.
     * todo: implement original values for all rooms etc for real re-start.
     */
    @Transactional
    public void restart(Avatar avatar) throws MenuException {
        log.debug("Restart game");
        avatar.setHealthToMax();
        msi.enterDungeon(avatar);
        avatarInterface.save(avatar);

        if (true) {
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

            if (avatar != null) {
                monsters = avatar.getCurrRoom().getMonsters();
                directions = avatar.getCurrRoom().getDirections();
            } else { break mainGameLoop; }

            try {
                System.out.println(buildStateInfo(avatar));
                amr.display(avatar);

            } catch (MenuException e) {
                log.debug("Quit the game. Breaking mainGameLoop.");
                System.out.println("Quit the current game.");
                break mainGameLoop;

            } catch (GameOverException e) {
                log.debug("Game over. Breaking mainGameLoop.");
                System.out.println("Your avatar has died in battle...");
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
     * todo: split the method up, possibly.
     */
    private String buildStateInfo(Avatar avatar) {
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
                String.format("\n%s health: %s", avatar.getName(), avatar.getCurrHealth()) +
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



    // ********************** Helper Methods ********************** //

}
