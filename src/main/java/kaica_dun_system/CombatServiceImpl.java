package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.RoomInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
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
    public boolean roomAutoCombat(Avatar avatar) {
        boolean avatarWins = true;
        Room room = avatar.getCurrRoom();
        //Disconnect the monster->room pointers to make the link unidirectional.
        //TODO ugly PH, rework. Might not be needed, test later. Depends on orphan removal and how that works.
        for (Monster m : room.getMonsters()) {
            m.setRoom(null);
        }

        autoCombat(avatar, room.getMonsters());

        //Reestablish db integrity if the monsters win.
        if (!room.getMonsters().isEmpty()) {
            avatarWins = false;
            for (Monster m : room.getMonsters()) {
                m.setRoom(room);
            }
        }

        avatarInterface.save(avatar);
        roomInterface.save(room);

        return avatarWins;
    }

    /**
     * Will exit when the monster list is empty or when avatar health drops to less than one.
     *
     * @param a         the avatar fighting the monsters
     * @param monsters  a list of the monsters to be fought
     */
    private void autoCombat(Avatar a, List<Monster> monsters) {

        //one round of combat per loop through the while-loop
        //TODO make not-ugly logic.
        while (a.getCurrHealth() > 0) {

            //remove all nulls from the LinkedList "monsters".
            while (monsters.remove(null));

            //break loop if all monsters are dead
            if (monsters.isEmpty()) {
                System.out.println("The corpses of your enemies litter the floor of the room. Silence falls.");
                break;
            }

            /*TODO Health should be evaluated after each hit, but the Avatar hitting Monster after Avatar is
             * dead shouldn't matter much.
             */
            combatRound(a, monsters);

            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getCurrHealth() <= 0) {
                    System.out.println(monsters.get(i).getName() + " dies");
                    monsters.remove(i);
                }
            }
        }
    }

    private void combatRound(Avatar a, List<Monster> monsters) {
        for (Monster m : monsters) {
            int monsterDealsDamage = m.hit(a);
            System.out.println(m.getName() + " hits " + a.getName() + " for " + monsterDealsDamage + " damage");
            Util.sleeper(1200);
        }
        int avatarDealsDamage = a.hit(monsters.get(0));
        System.out.println(a.getName() + " hits " + monsters.get(0).getName() + " for " + avatarDealsDamage + " damage");
        Util.sleeper(1200);
    }
}
