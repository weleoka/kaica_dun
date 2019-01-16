package kaica_dun.dao;

import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun.entities.RoomType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class RoomInterfaceImpl implements RoomInterfaceCustom {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Read from storage and find the first room in a dungeon
     *
     * @param dungeon a String to query with
     * @return roomid       a Long
     */
    //@Override
    @Transactional
    public UUID findFirstRoomInDungeon(Dungeon dungeon) {
        log.debug("Searching for first room in Dungeon: {}", dungeon.getDungeonId());
        TypedQuery<UUID> query = this.entityManager.createNamedQuery("Room.fetchDungeonFirstRoom", UUID.class);
        query.setParameter("dungeonId", dungeon);
        //List<Long> results = query.getResultList();
        UUID result = query.getSingleResult();

        return result;
    }


    /**
     * Read from storage and find the first room in a dungeon by its special enum identifier
     *
     * @param dungeon       an instance of a dungeon to look in
     * @param roomType      an instance of the enum to look for
     *
     * @return roomId       Long
     */
    //@Override
    @Transactional
    public List findRoomsInDungeonByEnum(Dungeon dungeon, RoomType roomType) {
        log.debug("Searching for first room in Dungeon: {}", dungeon.getDungeonId());
        TypedQuery<UUID> query = this.entityManager.createNamedQuery("Room.findRoomsInDungeonByEnum", UUID.class);
        query.setParameter("dungeonId", dungeon);
        query.setParameter("roomType", roomType);
        List<UUID> results = query.getResultList();
        //Long result = query.getSingleResult();

        return results;
    }



    /**
     * Read from storage and find the last room in a dungeon
     *
     * @param dungeon a String to query with
     * @return roomid       a Long
     */
    //@Override
    @Transactional
    public UUID findLastRoomInDungeon(Dungeon dungeon) {
        log.debug("Searching for last room in Dungeon: {}", dungeon.getDungeonId());
        TypedQuery<UUID> query = this.entityManager.createNamedQuery("Room.findLastRoomInDungeon", UUID.class);
        query.setParameter("dungeonId", dungeon);
        UUID result = query.getSingleResult();

        return result;
    }



    /**
     *
     * @param room
     * @return
     */
    @Transactional
    public List<UUID> findAliveMonstersInRoom(Room room) {
        log.debug("Searching for alive monsters in room in Dungeon: {}", room.getId());
        TypedQuery<UUID> query = this.entityManager.createNamedQuery("Room.findAliveMonstersInRoom", UUID.class);
        query.setParameter("roomId", room);
        List<UUID> results = query.getResultList();

        return results;
    }
}
