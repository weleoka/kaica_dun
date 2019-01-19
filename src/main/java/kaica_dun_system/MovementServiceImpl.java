package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Class that deals with assigning a new value for current room to avatar depending on the choices made by player
 *
 */
@Service
@EnableTransactionManagement
public class MovementServiceImpl {

    // Fields declared
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private AvatarInterface avatarInterface;


    /**
     * Find the first room if the dungeons rooms are EAGER loaded.
     *
     * @param dungeon
     * @return
     */
    public Room fetchDungeonFirstRoom(Dungeon dungeon) {
        Room room = null;
        for (Room r : dungeon.getRooms()){
            if(r.getRoomType() == RoomType.FIRST01) {
                room = r;
            }
        }
        log.debug("Fetching first room (id: {}) of the dungeon.", room.getId());
        return room;
    }


    /**
     * Enter the dungeon by an entrance.
     * @param avatar
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void enterDungeon(Avatar avatar) {
        Room firstRoom = fetchDungeonFirstRoom(avatar.getCurrDungeon());
        avatar.setCurrRoom(firstRoom);  //Enter first room of dungeon, always on index 0
        log.debug("Dropping avatar into room (id: {}) -> good luck!.", firstRoom.getId());
        avatarInterface.save(avatar);
    }


    /**
     * Leave the dungeon by an exit.
     *
     * @param avatar
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void exitDungeon(Avatar avatar) {
        avatar.setCurrRoom(null);           //Exit the room
        avatar.setCurrDungeon(null);        //Exit the dungeon
        avatarInterface.save(avatar);       //commit to db
    }


    /**
     * Move the avatar to a new Room.
     *
     * @param avatar        the avatar to be moved
     * @param direction     the direction to move the avatar
     * @return              the room that you moved the avatar to, returns null if the avatar has exited the dungeon
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Room moveAvatar(Avatar avatar, Direction direction) {
        Dungeon dungeon = avatar.getCurrDungeon();
        int directionNum = direction.ordinal();
        moveAvatar(avatar, dungeon, directionNum);
        Room newRoom = avatar.getCurrRoom();

        if (newRoom != null) {
            log.debug("Moved avatar to room with index '{}'", newRoom.getRoomIndex());
            avatarInterface.save(avatar);

        } else {
            log.debug("Moved avatar out of the dungeon");
            exitDungeon(avatar); // Not sure about calling the method with avatar as avatar is already in the class.
        }

        return newRoom;
    }


    /**
     * Handles the finding of the next room by user input parameter in the chain of rooms
     * created for the dungeon structure.
     *
     * @param avatar
     * @param dungeon
     * @param direction
     */
    private void moveAvatar(Avatar avatar, Dungeon dungeon, int direction) {
        int currRoomIndex = avatar.getCurrRoom().getRoomIndex();
        Set<Room> rooms = dungeon.getRooms();
        switch (direction) {
            case 0:
                //TODO simplify logic.
                int northIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                for (Room r : rooms) {
                    if (r.getRoomIndex() == northIndex) {
                        avatar.setCurrRoom(r);
                    }
                }
                break;
            case 1:
                int eastIndex = avatar.getCurrDungeon().getEastIndex(currRoomIndex);
                for (Room r : rooms) {
                    if (r.getRoomIndex() == eastIndex) {
                        avatar.setCurrRoom(r);
                    }
                }
                break;
            case 2:
                int southIndex = avatar.getCurrDungeon().getSouthIndex(currRoomIndex);
                for (Room r : rooms) {
                    if (r.getRoomIndex() == southIndex) {
                        avatar.setCurrRoom(r);
                    }
                }
                break;
            case 3:
                int westIndex = avatar.getCurrDungeon().getWestIndex(currRoomIndex);
                for (Room r : rooms) {
                    if (r.getRoomIndex() == westIndex) {
                        avatar.setCurrRoom(r);
                    }
                }
                break;
            case 4:
                exitDungeon(avatar);
                //User has exited the dungeon
                //TODO Do some more stuff!
                break;
            case 9:
                // User decides to stay in original room
                break;
            default:
                break;
        }
    }

}
