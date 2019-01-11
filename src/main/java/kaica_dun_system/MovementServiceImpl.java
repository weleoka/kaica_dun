package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class that deals with assigning a new value for current room to avatar depending on the choices made by player
 *
 */
@Service
@EnableTransactionManagement
public class MovementServiceImpl {

    // Fields declared
    private static final Logger log = LogManager.getLogger();
    private Avatar avatar;
    private Room room;

    @Autowired
    private AvatarInterface avatarInterface;


    /**
     * Enter the dungeon by an enterance.
     *
     * @param avatar
     * @param dungeon
     */
    public void enterDungeon(Avatar avatar, Dungeon dungeon) {
        avatar.setCurrRoom(dungeon.getRooms().get(0));  //Enter first room of dungeon, always on index 0
        avatar.setCurrDungeon(dungeon);
        avatarInterface.save(avatar);       //commit to db
    }


    /**
     * Leave the dungeon by an exit.
     *
     * @param avatar
     */
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
    @Transactional
    public Room moveAvatar(Avatar avatar, Direction direction) {
        Dungeon dungeon = avatar.getCurrDungeon();
        int directionNum = direction.ordinal();
        moveAvatar(avatar, dungeon, directionNum);
        //TODO the user might have exited the dungeon at this point, handle!

        //Persist the changes
        avatarInterface.save(avatar);

        return avatar.getCurrRoom();
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

        switch (direction) {
            case 0:
                //TODO simplify logic.
                int northIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room northRoom = dungeon.getRooms().get(northIndex);
                avatar.setCurrRoom(northRoom);
                break;
            case 1:
                int eastIndex = avatar.getCurrDungeon().getEastIndex(currRoomIndex);
                Room eastRoom = dungeon.getRooms().get(eastIndex);
                avatar.setCurrRoom(eastRoom);
                break;
            case 2:
                int southIndex = avatar.getCurrDungeon().getSouthIndex(currRoomIndex);
                Room southRoom = dungeon.getRooms().get(southIndex);
                avatar.setCurrRoom(southRoom);
                break;
            case 3:
                int westIndex = avatar.getCurrDungeon().getWestIndex(currRoomIndex);
                Room westRoom = dungeon.getRooms().get(westIndex);
                avatar.setCurrRoom(westRoom);
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
