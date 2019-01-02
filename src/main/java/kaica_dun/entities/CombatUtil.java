package kaica_dun.entities;

import java.util.List;
import java.util.Queue;

public class CombatUtil {

    CombatUtil() {}

    /**
     * Will exit when the monster list is empty or when avatar health drops to less than one.
     * @param a
     * @param monsters
     */
    public void autoCombat(Avatar a, List<Monster> monsters) {

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
