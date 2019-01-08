package kaica_dun_system;

import kaica_dun.dao.*;
import kaica_dun.entities.*;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * A class which handles the main entry, setup and leaving of a dungeon game.
 *
 * The main points of interest for this service is:
 * - Monsters
 * - Directions
 * ... - Items?
 *
 * This class will maintain a log of progress
 *
 * @author Kai Weeks
 *
 */
@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    private static final Logger log = LogManager.getLogger();

    private List<Monster> monsters;
    private List<Direction> directions;

    @Autowired
    UserInterface ui;

    @Autowired
    UserServiceImpl usi;

    @Autowired
    AvatarInterface avatarInterface;

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
    MenuLoggedIn menuLoggedIn;

    @Autowired
    EntityManager entityManager;

    @Autowired
    MonsterInterface monsterInterface;


    @Autowired
    RoomInterfaceCustom roomInterfaceCustom;

    @Autowired
    SessionFactory sessionFactory;


    /**
     * Creates a new dungeon and calles the operations to activate it.
     */
    public void playNew() throws MenuException {
        gsi.setDungeon(gsi.makeStaticDungeon());
        gsi.resetAvatar();
        gsi.startDungeon(); // todo: move out to Movement class
        printDebugInfo("New game: ");
        //UiString.printLoadingIntro();
        //UiString.printGameIntro();
        play();
    }


    /**
     * Resume a game that has been paused.
     */
    public void resume() throws MenuException {

        try {
            Room room = gsi.getAvatar().getCurrRoom();

        } catch (NullPointerException e) {
            log.warn("No Current room is set for Avatar. Can't resume game.");
            throw new MenuException("Quit the current game");
        }
        printDebugInfo("Resume game: ");
        UiString.printLoadingIntro();
        play();
    }

    /**
     * Restart the current game with the same parameters.
     * Currently it is a random re-generation, as original parameters were not saved.
     * todo: implement original values for all rooms etc for real re-start.
     */
    public void restart() throws MenuException {
        printDebugInfo("Restart game: ");
        gsi.resetAvatar();
        gsi.startDungeon();
        UiString.printLoadingIntro();
        UiString.printGameIntro();
        play();
    }



    /**
     * The method for initialising game loop and handling
     * exception bubbling thrown in nested loops.
     *
     * todo: move the string reports out to UiStrings.
     */
    @Override
    public void play() throws MenuException {
        log.debug("Starting game loop.");

        mainGameLoop:
        while(true) {
            //this.room = avatar.getCurrRoom();
            this.monsters = getMonstersCurrentRoom();
            this.directions = getDirectionsCurrentRoom();

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

            for (int i = 1; i < monsters.size() + 1; i++) {
                Monster monster = monsters.get(i - 1);
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

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Direction> getDirections() {
        return directions;
    }


    public List<Monster> getMonstersCurrentRoom() {
        return gsi.getAvatarCurrentRoom().getMonsters();
    }

    public List<Direction> getDirectionsCurrentRoom() {
        return gsi.getAvatarCurrentRoom().getExits();
    }




    public void printDebugInfo(String extra) {
        log.debug("{} (User: {}, Avatar: '{}', Dungeon: {})",
                extra,
                //gsi.getDungeon().getUser().getName(),
                usi.getAuthenticatedUser().getName(),
                gsi.getAvatar().getName(),
                gsi.getDungeon().getDungeonId());
    }
}
