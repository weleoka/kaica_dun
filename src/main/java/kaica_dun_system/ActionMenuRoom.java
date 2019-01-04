package kaica_dun_system;


import kaica_dun.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The actions in a room concerning looking at things
 * and attacking monsters. finally also selecting movement options,
 * But you can't leave a room until all the monsters are dead, dead, dead!
 *
 * This menu also brings you to the Avatar menu.
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

    private String mainOutput;
    private String lookAtOutput;
    private String battleOutput;
    private String moveOutput;
    private Room room;

    ActionMenuRoom() {

    }


    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display() {
        int selection;
        HashMap<Integer, String> options = buildMainOptions();

        Set<Integer> validOptions = options.keySet();
        String formatted = UI_strings.menuHeader4 + mainOutput + UI_strings.makeSelectionPrompt;

        selection = getUserInput(validOptions, formatted);

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



    /**
     * Depending on what is in the room the menu needs
     * to be built differently.
     *
     * todo: make it dynamic so that options are sequentially numbered.
     *
     * @return
     */
    private HashMap<Integer, String> buildMainOptions() {
        String output = "";
        HashMap<Integer, String> options = null;

        room = aesi.getRoom();
        HashMap<Integer, Monster> opt1 = buildLookAtOptions();
        HashMap<Integer, Monster> opt2 = buildBattleOptions();
        HashMap<Integer, Direction> opt3 = buildMoveOptions();

        if (opt1 != null) {
            output += String.format("\n[1] - Look at things");
            options.put(1, "lookAt");
        }
        if (opt2 != null) {
            output += String.format("\n[2] - Fight monsters ");
            options.put(2, "fight");
        }
        if (opt3 != null) {
            output += String.format("\n[3] - Movevement options");
            options.put(3, "move");
        }

        output += String.format("\n[4] - Inventory management");
        options.put(4, "inventory");
        output += String.format("\n[9] - Game menu");
        options.put(9, "gameMenu");

        return options;
    }


    /**
     * Different things in a room need to be decribed.
     * These are: monsters, exits, chests, other avatars?
     *
     * todo: implement looking at items and other things, not only monsters.
     *
     * @return
     */
    private HashMap<Integer, Monster> buildLookAtOptions() {
        String output = "";
        HashMap<Integer, Monster> options = null;
        Room room = aesi.getRoom();
        log.debug("Looking at items in room id: {}.", room.getId());

/*        List<Item> items = room.getItems();
        for (int i = 0; i < items.size(); i++) {
            output +=  String.format("[%s] - Item: %s.", i, item.getDescription();
        }*/

        List<Monster> monsters = room.getMonsters();

        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            output +=  String.format("\n[%s] - %s.", i, monster.getType());
            options.put(i, monster);
        }

        //output = "There are no items in the room.";

        lookAtOutput = output;

        return options;
    }


    /**
     * Could be defined so that individual monsters could be attacked.
     * Currently just runs auto-battle.
     *
     * @return
     */
    private HashMap<Integer, Monster> buildBattleOptions() {
        String output = "";
        HashMap<Integer, Monster> options = null;
        Room room = aesi.getRoom();

        List<Monster> monsters = room.getMonsters(); // SELECT Fighter FROM Fighter f where f.RoomId = :currRoom

        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            output += String.format("\n[%s] - %s (HP: %s)", i + 1, monster.getType(), monster.getCurrHealth());
            options.put(i, monster);
        }

        // Doing this because of berserk mode.
        output = "\n----> Auto battle is enabled. Stand back and watch the slaughter.";

        battleOutput = output;

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
        String output = "";
        HashMap<Integer, Direction> options = null;
        List<Direction> exits = room.getExits();


        for (int i = 0; i < exits.size(); i++) {
            Direction direction = exits.get(i);
            output += String.format("[%s] - %s", direction.getDirectionNumber(), direction.toString());
        }

        // OR enhanced loop:

        for (Direction exit: room.getExits()) {
            output += String.format("[%s] - %s", exit.getDirectionNumber(), exit.toString());
        }

        moveOutput = output;

        return options;
    }




    private void selectMainOptions() {

    }


    private void selectLookAtOption() {
        // todo: needs to be able to look at everything in room and its exits.
        HashMap<Integer, Monster> options = buildLookAtOptions();
        Set<Integer> validOptions = options.keySet();

        String formatted = UI_strings.lookAtMenuHeader + moveOutput + UI_strings.makeSelectionPrompt;

        int selection = getUserInput(validOptions, moveOutput);

        Monster monster = options.get(selection);
        System.out.println(monster.getDescription());
    }


    private void selectBattleOption() {

        csi.roomAutoCombat(aesi.getAvatar());
    }


    private void selectMoveOption() {
        HashMap<Integer, Direction> options = buildMoveOptions();
        Set<Integer> validOptions = options.keySet();

        String formatted = UI_strings.moveMenuHeader + moveOutput + UI_strings.makeSelectionPrompt;

        int selection = getUserInput(validOptions, formatted);

        Direction direction = options.get(selection);
        msi.moveAvatar(aesi.getAvatar(), direction);
    }


}
