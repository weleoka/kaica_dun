package kaica_dun.dao;

import kaica_dun.entities.Room;

public interface RoomInterfaceCustom {
    Long findFirstRoomInDungeon(Long dungeonId);

    Long findLastRoomInDungeon(Long dungeonId);
}
