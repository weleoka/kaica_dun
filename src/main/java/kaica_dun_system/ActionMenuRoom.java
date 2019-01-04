package kaica_dun_system;


import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The actions in a room concerning looking at things
 * and attacking monsters. finally also selecting movement options,
 * But you can't leave a room until all the monsters are dead, dead, dead!
 */
public class ActionMenuRoom extends ActionMenu {


    @Autowired
    ActionEngineServiceImpl aesi;

    String lookatOutput;

    String battleOutput;

    String moveOutput;



    ActionMenuRoom() {
        buildLookAtOptions();
        buildBattleOptions();
        buildMoveOptions();
    }


    /**
     * Different things in a room need to be decribed.
     * These are: monsters, exits, chests, other avatars?
     *
     * @return
     */
    private String buildLookAtOptions() {
        Room room = aesi.getRoom();

        //room.getItems();

        return null;
    }


    /**
     * Could be defined so that individual monsters could be attacked.
     * Currently just runs auto-battle.
     *
     * @return
     */
    private String buildBattleOptions() {
        Room room = aesi.getRoom();
        // SELECT Fighter FROM Fighter f where f.RoomId = :currRoom
        List<Monster> monsters = room.getMonsters();

        String options = "";

        for (int i = 0; i < monsters.size(); i++) {
        Monster monster = monsters.get(i);
            options += String.format("[%s] - %s (HP: %s", i + 1, monster.getType(), monster.getCurrHealth());
        }

        options = "[1] - Auto battle";

        return options;
    }



    private void selectBattleOption() {

    }


    /**
     * Every room has at least one exit. But many have multiple,
     * this gives the output options for these.
     *
     * @return
     */
    private String buildMoveOptions() {

        return null;
    }
}
