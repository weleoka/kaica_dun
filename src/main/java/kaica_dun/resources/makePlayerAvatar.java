package kaica_dun.resources;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Player;
import kaica_dun.entities.PlayerAvatarInherited;
import kaica_dun.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class makePlayerAvatar {
    private Player player;

    /**
     * Create a new dungeon with static structure for testing, belonging to a player, that we can then save to the db.
     *
     * @param player        the Player that the Dungeon belongs to
     */
    public makePlayerAvatar(Player player){
        this.player = player;
    }

    public PlayerAvatarInherited makePlayerAvatar() {

        //TODO PH-return
        return null;
        }
}
