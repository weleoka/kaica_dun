package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * The actions in a room concerning looking at things
 * and attacking monsters. Finally also selecting movement options,
 * But you can't leave a room until all the monsters are dead, dead, dead!
 *
 * This menu also brings you to the Avatar menu.
 *
 * It works by always calling buildMainOptions(), that will then call other option scenarios
 * valid for the current room and build menu choices from that.
 *
 * todo: consider only building menu options after selecting a main Action menu choice so speed up loop.
 * todo: decide at exactly what interval/loop sequence to refresh the loops.
 */
@Component
public class ActionMenuRoom extends ActionMenu {

    @Autowired
    ActionEngineServiceImpl aesi;

    @Autowired
    MovementServiceImpl msi;

    @Autowired
    CombatServiceImpl csi;

    @Autowired
    MenuInGame mig;

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private RoomInterface roomInterface;



    private String mainOutput;
    private HashMap<Integer, String> mainOptions;

    private String lookAtOutput;
    private HashMap<Integer, Monster> lookAtOptions; // Currently only look at Monsters.

    private String battleOutput;
    private HashMap<Integer, Monster> battleOptions;

    private String moveOutput;
    private HashMap<Integer, Direction> moveOptions;

    ActionMenuRoom() {}


    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display() throws MenuException, GameOverException, GameWonException {
        int selection;
        mainOptions = buildMainOptions();

        String str = UiString.menuHeader4 + mainOutput + UiString.makeSelectionPrompt;

        selection = getUserInput(mainOptions.keySet(), str);

        switch (selection) {
            case 1:
                selectLookAtOption();
                break;

            case 2:
                selectBattleOption();
                break;

            case 3:
                selectMoveOption();
                break;

            case 9:
                mig.display(false);
                break;
        }
    }




    // ********************** Builder Methods ********************** //

    /**
     * Depending on what is in the room the menu needs
     * to be built differently.
     *
     * todo: make it dynamic so that options are sequentially numbered.
     * todo: move strings out to UiString class for language adaption.
     *
     * @return
     */
    private HashMap<Integer, String> buildMainOptions() {
        StringBuilder output = new StringBuilder();
        HashMap<Integer, String> options = new HashMap<>();

        lookAtOptions = buildLookAtOptions();
        battleOptions = buildBattleOptions();
        moveOptions = buildMoveOptions();


        if (lookAtOptions.size() > 0) {
            output.append(String.format("\n[1] - Look at things"));
            options.put(1, "lookAt"); // This should be dynamic and not always [1]
        }
        if (battleOptions.size() > 0) {
            output.append(String.format("\n[2] - Fight monsters "));
            options.put(2, "fight");
        }
        if (moveOptions.size() > 0) {
            output.append(String.format("\n[3] - Movement options"));
            options.put(3, "move");
        }

        output.append(String.format("\n[4] - Inventory management(not working)"));
        options.put(4, "inventory");

        output.append(String.format("\n[9] - Game menu"));
        options.put(9, "gameMenu"); // This can always be at [9].

        mainOutput = output.toString();

        return options;
    }


    /**
     * Different things in a room need to be decribed.
     * These are: monsters, exits, chests, other avatars?
     *
     * todo: implement looking at items and other things, not only monsters.
     */
    private HashMap<Integer, Monster> buildLookAtOptions() {
        StringBuilder output = new StringBuilder();
        HashMap<Integer, Monster> options = new HashMap<>();

/*        List<Item> items = room.getItems();
        for (int i = 0; i < items.size(); i++) {
            output +=  String.format("[%s] - Item: %s.", i, item.getDescription();
        }*/

        List<Monster> monsters = aesi.room.getMonsters();

        //log.debug("There are {} monsters in the room (id: {}).", monsters.size(), room.getId());

        for (int i = 1; i < monsters.size() + 1; i++) {
            Monster monster = monsters.get(i - 1);
            //log.debug("Found: {}(id: {}), building look-at options.", monster.getType(), monster.getId());
            output.append(String.format("\n[%s] - %s.", i, monster.getType()));
            options.put(i, monster);
        }

        //output = "There are no items in the room.";

        lookAtOutput = output.toString();

        return options;
    }


    /**
     * Could be defined so that individual monsters could be attacked.
     * Currently just runs auto-battle.
     *
     * @return
     */
    private HashMap<Integer, Monster> buildBattleOptions() {
        StringBuilder output = new StringBuilder();
        HashMap<Integer, Monster> options = new HashMap<>();

        List<Monster> monsters = aesi.room.getMonsters(); // SELECT Fighter FROM Fighter f where f.RoomId = :currRoom

        for (int i = 1; i < monsters.size() + 1; i++) {
            Monster monster = monsters.get(i - 1);
            output.append(String.format("\n[%s] - %s (HP: %s)", i + 1, monster.getType(), monster.getCurrHealth()));
            options.put(i, monster);
        }

        // Doing this because of berserk mode.
        output.append("\n----> Auto battle is enabled. Stand back and watch the slaughter.");

        battleOutput = output.toString();

        return options;
    }


    /**
     * Every room has at least one exit. But many have multiple,
     * this gives the output options for these.
     *
     * Because direction is a constant enum the numbered options generated do not have to be kept
     * with a reference to their actual counterparts in the system.
     *
     * @return
     */
    private HashMap<Integer, Direction> buildMoveOptions() {
        StringBuilder output = new StringBuilder();
        HashMap<Integer, Direction> options = new HashMap<>();

        List<Direction> exits = aesi.room.getExits();

        //log.debug("There are {} exits from the room (id: {})", exits.size(), room.getId());

        for (Direction direction: exits) {
            output.append(String.format("\n[%s] - %s", direction.getDirectionNumber(), direction.toString()));
            options.put(direction.getDirectionNumber(), direction);
        }

        moveOutput = output.toString();

        return options;

        /*
        for (int i = 1; i < exits.size() + 1; i++) {
            Direction direction = exits.get(i);
            output.append(String.format("\n[%s] - %s", direction.getDirectionNumber(), direction.toString()));
        }*/
    }




    // ********************** Selection Methods ********************** //


    private void selectLookAtOption() {
        String str = UiString.lookAtMenuHeader + lookAtOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(lookAtOptions.keySet(), str);
        Monster monster = lookAtOptions.get(sel);
        System.out.println(monster.getDescription());
        Util.sleeper(1400);
    }


    private void selectBattleOption() throws GameOverException  {
        String str = UiString.battleMenuHeader + battleOutput + UiString.makeSelectionPrompt;
        System.out.println(str);
        //int sel = getUserInput(battleOptions.keySet(), str);
        //Monster monster = battleOptions.get(sel);
        //System.out.printf("\nYou are attacking: %s", monster.toString());

        System.out.println("(Fighting individual monsters disabled. Commencing auto- battle...)");
        Util.sleeper(1200);
        List<Monster> monsters = csi.autoCombat(aesi.getAvatar(), aesi.room.getMonsters());
        aesi.room.setMonsters(monsters);
        roomInterface.save(aesi.room);      // Save the updated list of monsters.
        avatarInterface.save(aesi.avatar); // Save the HP to database.
    }


    private void selectMoveOption() {
        moveOptions = buildMoveOptions();
        String str = UiString.moveMenuHeader + moveOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(moveOptions.keySet(), str);
        Direction direction = moveOptions.get(sel);
        //log.debug("Movement selection made {}, resulting in direction: {}", sel, direction.toString());
        Room newRoom = msi.moveAvatar(aesi.getAvatar(), direction);
        System.out.printf("\nMoved Avatar to the next room to the %s", direction.toString());
        //log.debug("Moved avatar to new room: '{}'", newRoom.getId());
    }
}
