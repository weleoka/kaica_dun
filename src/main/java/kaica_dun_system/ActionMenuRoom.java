package kaica_dun_system;


import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Monster;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
 *
 * @author Kai Weeks
 *
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
    RoomInterface roomInterface;


    private String mainOutput;
    private HashMap<Integer, String> mainOptions;

    private String lookAtOutput;
    private HashMap<Integer, Monster> lookAtOptions; // Currently only look at Monsters.
    //private HashMap<Describable> describables;

    private String battleOutput;
    private HashMap<Integer, Monster> battleOptions;
    private List<Monster> monsters;

    private String moveOutput;
    private HashMap<Integer, Direction> moveOptions;
    private List<Direction> directions;

    ActionMenuRoom() {}


    /**
     * Display the main action options after building all the sub-menu items.
     *
     */
    void display() throws MenuException, GameOverException, GameWonException {
        int selection;

        this.monsters = aesi.getMonsters();
        this.directions = aesi.getDirections();
        //this.describables = aesi.getDescribables();

        buildLookAtOptions();
        buildBattleOptions();
        buildMoveOptions();

        buildMainOptions();

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
                if (!monsters.isEmpty()) {
                    System.out.println("Can't move because there are enemies in the room!! Kill them first.");
                    Util.sleeper(2200);
                } else {
                    selectMoveOption();
                }
                selectMoveOption(); // Ignore the monsters in the room. Development.
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
    private void buildMainOptions() {
        StringBuilder output = new StringBuilder();
        mainOptions = new HashMap<>();

        if (lookAtOptions.size() > 0) {
            output.append(String.format("\n[1] - Look at things"));
            mainOptions.put(1, "lookAt"); // This should be dynamic and not always [1]
        }
        if (battleOptions.size() > 0) {
            output.append(String.format("\n[2] - Fight monsters "));
            mainOptions.put(2, "fight");
        }
        if (moveOptions.size() > 0) {
            output.append(String.format("\n[3] - Movement options"));
            mainOptions.put(3, "move");
        }

        output.append(String.format("\n[4] - Inventory management(not working)"));
        mainOptions.put(4, "inventory");

        output.append(String.format("\n[9] - Game menu"));
        mainOptions.put(9, "gameMenu"); // This can always be at [9].

        mainOutput = output.toString();
    }


    /**
     * Different things in a room need to be decribed.
     * These are: monsters, directions, chests, other avatars?
     *
     * todo: implement looking at items and other things, not only monsters.
     */
    private void buildLookAtOptions() {
        StringBuilder output = new StringBuilder();
        lookAtOptions = new HashMap<>();

        //log.debug("There are {} describables in the room (id: {}).", describables.size(), room.getId());

        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            //log.debug("Found: {}(id: {}), building look-at options.", monster.getType(), monster.getId());
            output.append(String.format("\n[%s] - %s.", i + 1, monster.getType()));
            lookAtOptions.put(i + 1, monster);
        }
        //output.append(UiString.noDescribablesVisible);
        lookAtOutput = output.toString();
    }


    /**
     * Could be defined so that individual monsters could be attacked.
     * Currently just runs auto-battle.
     *
     * @return
     */
    private void buildBattleOptions() {
        StringBuilder output = new StringBuilder();
        battleOptions = new HashMap<>();

        //log.debug("There are {} monsters in the room (id: {})", monsters.size(), room.getId());

        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);

            if (monster.isAlive()) {
                output.append(String.format("\n[%s] - %s (HP: %s)", i + 1, monster.getType(), monster.getCurrHealth()));
                battleOptions.put(i + 1, monster);
            }

        }
        output.append("\n----> Auto battle is enabled. Stand back and watch the slaughter.");
        battleOutput = output.toString();
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
    private void buildMoveOptions() {
        StringBuilder output = new StringBuilder();
        moveOptions = new HashMap<>();

        //log.debug("There are {} directions from the room (id: {})", directions.size(), room.getId());

        for (Direction direction: directions) {
            output.append(String.format("\n[%s] - %s", direction.getDirectionNumber(), direction.toString()));
            moveOptions.put(direction.getDirectionNumber(), direction);
        }
        moveOutput = output.toString();
    }




    // ********************** Selection Methods ********************** //


    private void selectLookAtOption() {
        String str = UiString.lookAtMenuHeader + lookAtOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(lookAtOptions.keySet(), str);
        Monster monster = lookAtOptions.get(sel);
        System.out.println(monster.getDescription());
        Util.sleeper(1400);
    }

    void selectBattleOption() throws GameOverException, GameWonException {
        String str = UiString.battleMenuHeader + battleOutput + UiString.makeSelectionPrompt;
        System.out.println(str);

        // AUTO-BATTLE
        System.out.println("(Fighting individual monsters disabled. Commencing auto- battle...)");
        Util.sleeper(1200);
        List<Monster> monsters = new ArrayList<>(battleOptions.values());
        csi.autoCombat(gsi.getAvatar(), monsters);

        monsters.clear();
        battleOptions.clear();

        // END auto-battle


        // ONE-ON-ONE
        //int sel = getUserInput(battleOptions.keySet(), str);
        //Monster monster = battleOptions.get(sel);
        //List<Monster> monstersModified = csi.combat(aesi.getAvatar(), monsters);
        //System.out.printf("\nYou are attacking: %s", monster.toString());
        // END one-on-one


        //aesi.getAvatarCurrentRoom().setMonsters(new ArrayList<>()); // Autocombat always results in total extinction.
        //aesi.getAvatarCurrentRoom().setMonsters(monstersModified);


        //roomInterface.save(aesi.getAvatarCurrentRoom());    // Save the updated list of monsters.
        gsi.updateAvatar(); // Save the HP to database.
    }


    private void selectMoveOption() {
        String str = UiString.moveMenuHeader + moveOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(moveOptions.keySet(), str);
        Direction direction = moveOptions.get(sel);
        //log.debug("Movement selection made {}, resulting in direction: {}", sel, direction.toString());
        msi.moveAvatar(gsi.getAvatar(), direction);
        System.out.printf("\nMoved Avatar to the next room to the %s", direction.toString());
        //log.debug("Moved avatar to new room: '{}'", newRoom.getId());
        gsi.updateAvatar();
        clearOptions();
    }

    private void clearOptions() {
        this.battleOptions.clear();
        //this.monsters.clear();

        this.moveOptions.clear();
        //this.directions.clear();

        this.lookAtOptions.clear();
        //this.describables.clear();

        this.mainOptions.clear();

    }
}