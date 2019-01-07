package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import kaica_dun.util.GameOverException;
import kaica_dun.util.GameWonException;
import kaica_dun.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@EnableTransactionManagement
public class CombatServiceImpl {

    // Singleton
    private static CombatServiceImpl ourInstance = new CombatServiceImpl();
    public static CombatServiceImpl getInstance() {
        return ourInstance;
    }
    private CombatServiceImpl() {}

    // Fields declared
    private static final Logger log = LogManager.getLogger();


    /**
     * Will exit when the monster list is empty or when avatar health drops to less than one.
     *
     * Because the list passed in is mutated it can be a good idea to create a new list and
     * pass that back out from the method again. Like the following:
     *
     *     List returnList = new ArrayList();
     *     returnList.addAll(someList);
     *     returnList.add(someObject);
     *     return Collections.unmodifiableList(returnList);
     *
     * todo: Health should be evaluated after each hit, but the Avatar hitting Monster after Avatar is
     *  dead shouldn't matter much.
     *
     * @param avatar         the avatar fighting the monsters
     * @param monsters       a list of monsters to fight
     */
    public List autoCombat(Avatar avatar, List<Monster> monsters) throws GameOverException {
        List newList = new ArrayList();

        while (!monsters.isEmpty()) {

            if (avatar.getCurrHealth() <= 0) { //break loop if avatar is dead.
                throw new GameOverException(String.format("%s has fallen in battle.", avatar.getName()));
            }
            combatRound(avatar, monsters);
        }

        System.out.println("The corpses of your enemies litter the floor of the room. Silence falls.");

        return newList; // superfluous because there will never be another way out than death or no monsters.
    }


    /**
     * A round of combat.
     *
     * @param a
     * @param monsters
     * @return
     */
    private void combatRound(Avatar a, List<Monster> monsters) {

        for (Monster m : monsters) {
            int monsterDealsDamage = m.hit(a);
            System.out.println(m.getName() + " hits " + a.getName() + " for " + monsterDealsDamage + " damage");
            Util.sleeper(800);
        }

        int avatarDealsDamage = a.hit(monsters.get(0));
        System.out.println(a.getName() + " hits " + monsters.get(0).getName() + " for " + avatarDealsDamage + " damage");

        if (deathCheck(monsters.get(0))) {
            monsters.remove(0);
        }
        Util.sleeper(800);
    }


    /**
     * Convenience method for checking if a monster is dead.
     * @param m
     * @return
     */
    private boolean deathCheck(Monster m) {
        boolean isDead = false;
        if (m.getCurrHealth() <= 0) {
            System.out.println(m.getName() + " dies");
            isDead = true;
        }
        return isDead;
    }
}
