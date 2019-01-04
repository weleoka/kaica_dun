package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Dungeon;

import java.util.List;

public interface MovementService {

    void moveAvatar(Avatar avatar, Direction direction);

    void enterDungeon(Avatar avatar, Dungeon dungeon);

    void exitDungeon(Avatar avatar);

    // ********************** Persistence Methods ********************** //

}
