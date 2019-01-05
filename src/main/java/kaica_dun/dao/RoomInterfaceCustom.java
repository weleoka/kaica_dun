package kaica_dun.dao;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;

public interface RoomInterfaceCustom {
    Long findFirstRoomInDungeon(Dungeon dungeon);

    Long findLastRoomInDungeon(Dungeon dungeon);
}
