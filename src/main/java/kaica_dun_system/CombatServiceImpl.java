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

import javax.transaction.Transactional;
import java.util.Set;

@Service
@EnableTransactionManagement
public class CombatServiceImpl {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private RoomInterface roomInterface;


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
     */
    @Transactional
    public void autoCombat(Avatar avatar) throws GameOverException, GameWonException {
        Room room = avatar.getCurrRoom();
        Set<Monster> monsters = room.getMonsters();

        while (!monsters.isEmpty()) {

            if (avatar.getCurrHealth() <= 0) { //break loop if avatar is dead.

                throw new GameOverException(String.format("%s has fallen in battle.", avatar.getName()));
            }
            combatRound(avatar, monsters);
        }
        roomInterface.save(room);
        avatarInterface.save(avatar); // Save the HP to database.
        System.out.println("The corpses of your enemies litter the floor of the room. Silence falls.");
    }


    /**
     * A round of combat.
     *
     * @param a
     * @param monsters
     * @return
     */
    public void combatRound(Avatar a, Set<Monster> monsters) throws GameWonException {
        for (Monster m : monsters) {
            int monsterDealsDamage = m.hit(a);
            System.out.println(m.getName() + " hits " + a.getName() + " for " + monsterDealsDamage + " damage");
            Util.sleeper(800);
        }

        Monster activeMonster = monsters.iterator().next();
        int avatarDealsDamage = a.hit(activeMonster);
        System.out.println(a.getName() + " hits " + activeMonster.getName() + " for " + avatarDealsDamage + " damage");

        if ((activeMonster.deathCheck())) {
            System.out.println(activeMonster.getName() + " dies");
            a.getCurrRoom().getMonsters().remove(activeMonster);
            monsters.remove(activeMonster); // remove from the array.

            if (activeMonster.getType() == "Dragon") {
                throw new GameWonException(String.format("Congratulations! You have slain the Dragon Smug."));
            }
        }
        Util.sleeper(800);
    }
}
