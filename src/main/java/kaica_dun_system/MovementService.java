package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;

public interface MovementService {

    Room moveAvatar(Avatar avatar, Direction direction);

    Room moveAvatar(Avatar avatar, int direction);

    void enterDungeon(Avatar avatar, Dungeon dungeon);

    void exitDungeon(Avatar avatar);

    // ********************** Persistence Methods ********************** //

}
