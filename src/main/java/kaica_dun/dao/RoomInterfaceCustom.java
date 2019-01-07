package kaica_dun.dao;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun.entities.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomInterfaceCustom {
    Long findFirstRoomInDungeon(Dungeon dungeon);

    List<Long> findRoomsInDungeonByEnum(Dungeon dungeon, RoomType roomType);

    Long findLastRoomInDungeon(Dungeon dungeon);

    List<Long> findAliveMonstersInRoom(Room room);
}
