package kaica_dun.resources;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;

import java.util.List;

public class CombatUtil {

    private CombatUtil() {}

    /**
     * Fight all the monsters in a given Room.
     *
     * @param a     the Avatar fighting
     * @param r     the Room whose monsters the avatar wants to engage
     */
    public static void roomAutoCombat(Avatar a, Room r) {

        //Disconnect the monster->room pointers to make the link unidirectional.
        //TODO ugly PH, rework. Might not be needed, test later. Depends on orphan removal and how that works.
        for (Monster m : r.getMonsters()) {
            m.setRoom(null);
        }

        autoCombat(a, r.getMonsters());

        //Reestablish db integrity if the monsters win.
        for (Monster m : r.getMonsters()) {
            m.setRoom(r);
        }
    }

    /**
     * Will exit when the monster list is empty or when avatar health drops to less than one.
     * @param a         the avatar fighting the monsters
     * @param monsters  a list of the monsters to be fought
     */
    private static void autoCombat(Avatar a, List<Monster> monsters) {

        //one round of combat per loop through the while-loop
        //TODO make not-ugly logic.
        while (a.getCurrHealth() > 0) {

            //remove all nulls from the LinkedList "monsters".
            while (monsters.remove(null));

            //break loop if all monsters are dead
            if (monsters.isEmpty()) {
                break;
            }

            /*TODO Health should be evaluated after each hit, but the Avatar hitting Monster after Avatar is
             * dead shouldn't matter much.
             */
            for (Monster m : monsters) {
                m.hit(a);
            }
            //hit first monster in list, shouldn't be null bc of null removal, I hope.
            a.hit(monsters.get(0));

            for (Monster m : monsters) {
                if (m.getCurrHealth() <= 0) {
                    //TODO Check if orphan removal on Room.monsters works as it should
                    monsters.remove(m);
                }
            }
        }
    }
}
