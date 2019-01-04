package kaica_dun.dao;

import kaica_dun.entities.Room;
import kaica_dun_system.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoomInterfaceImpl implements RoomInterfaceCustom {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private EntityManager entityManager;

    /**
     * Read from storage and find the first room in a dungeon
     *
     * @param dungeonId a String to query with
     * @return a User instance
     */
    //@Override
    @Transactional
    public Long findFirstRoomInDungeon(Long dungeonId) {
        log.debug("Searching for first room in Dungeon: {}", dungeonId);
        TypedQuery<Long> query = this.entityManager.createNamedQuery("Room.findFirstRoomInDungeon", Long.class);
        query.setParameter("dungeonId", dungeonId);
        List<Long> results = query.getResultList();

        return results.get(0);
    }



    /**
     * Read from storage and find the last room in a dungeon
     *
     * @param dungeonId a String to query with
     * @return a User instance
     */
    //@Override
    @Transactional
    public Long findLastRoomInDungeon(Long dungeonId) {
        log.debug("Searching for last room in Dungeon: {}", dungeonId);
        TypedQuery<Long> query = this.entityManager.createNamedQuery("Room.findLastRoomInDungeon", Long.class);
        query.setParameter("dungeonId", dungeonId);
        List<Long> results = query.getResultList();

        return results.get(0);
    }
}
