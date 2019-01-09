package kaica_dun.dao;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun.entities.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomInterfaceCustom {
    UUID findFirstRoomInDungeon(Dungeon dungeon);

    List<UUID> findRoomsInDungeonByEnum(Dungeon dungeon, RoomType roomType);

    UUID findLastRoomInDungeon(Dungeon dungeon);

    List<UUID> findAliveMonstersInRoom(Room room);
}
