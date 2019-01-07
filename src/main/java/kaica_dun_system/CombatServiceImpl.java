package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import kaica_dun.util.GameOverException;
import kaica_dun.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    @Autowired
    private AvatarInterface avatarInterface;

    @Autowired
    private RoomInterface roomInterface;

    /**
     * Fight all the monsters in a given Room.
     *
     * @param avatar     the Avatar fighting
     * @return      return true if avatar wins, false if avatar is defeated
     */
    public boolean roomAutoCombat(Avatar avatar) throws GameOverException {
        boolean avatarWins = true;
        Room room = avatar.getCurrRoom();
        autoCombat(avatar, room);
        //If room is not empty after combat, then the Avatar has lost.
        if (!room.getMonsters().isEmpty()) {
            avatarWins = false; //TODO use this to display message, set pointers and exit dungeon.
            throw new GameOverException("Your Avatar has fallen in battle.");
        }

        avatarInterface.save(avatar);

        return avatarWins;
    }

    /**
     * Will exit when the monster list is empty or when avatar health drops to less than one.
     *
     * @param a         the avatar fighting the monsters
     * @param room      the room where the fighting is taking place
     */
    private void autoCombat(Avatar a, Room room) {
        List<Monster> monsters = room.getMonsters();
        //one round of combat per loop through the while-loop
        //TODO make not-ugly logic.
        while (a.getCurrHealth() > 0) {

            //break loop if all monsters are dead
            if (monsters.isEmpty()) {
                System.out.println("The corpses of your enemies litter the floor of the room. Silence falls.");
                break;
            }

            /*TODO Health should be evaluated after each hit, but the Avatar hitting Monster after Avatar is
             * dead shouldn't matter much.
             */
            combatRound(a, room);
        }
    }

    private void combatRound(Avatar a, Room room) {
        List<Monster> monsters = room.getMonsters();
        for (Monster m : monsters) {
            int monsterDealsDamage = m.hit(a);
            System.out.println(m.getName() + " hits " + a.getName() + " for " + monsterDealsDamage + " damage");
            Util.sleeper(800);
        }
        int avatarDealsDamage = a.hit(monsters.get(0));
        System.out.println(a.getName() + " hits " + monsters.get(0).getName() + " for " + avatarDealsDamage + " damage");
        if (deathCheck(monsters.get(0))) {
            room.removeMonster(monsters.get(0));
        }
        Util.sleeper(800);
    }

    private boolean deathCheck(Monster m) {
        boolean isDead = false;
        if (m.getCurrHealth() <= 0) {
            System.out.println(m.getName() + " dies");
            isDead = true;
        }
        return isDead;
    }
}
