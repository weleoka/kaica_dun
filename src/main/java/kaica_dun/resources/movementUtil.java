package kaica_dun.resources;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Direction;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun_system.User;

public class movementUtil {

    private movementUtil() {}

    public static void moveCurrAvatar(User user, Direction direction) {
        Avatar avatar = user.getCurrAvatar();
        Dungeon dungeon = user.getCurrDungeon();
        moveAvatar(avatar, dungeon, direction);
        //TODO the user might have exited the dungeon at this point, handle!
    }

    private static void moveAvatar(Avatar avatar, Dungeon dungeon, Direction direction) {
        int currRoomIndex = avatar.getCurrRoom().getRoomIndex();
        Dungeon currDungeon = avatar.getCurrDungeon();
        int directionNum = direction.ordinal();

        switch (directionNum) {
            case 0:
                //TODO simplify logic.
                int northIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room northRoom = dungeon.getRooms().get(northIndex);
                avatar.setCurrRoom(northRoom);
                break;
            case 1:
                int eastIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room eastRoom = dungeon.getRooms().get(eastIndex);
                avatar.setCurrRoom(eastRoom);
                break;
            case 2:
                int southIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room southRoom = dungeon.getRooms().get(southIndex);
                avatar.setCurrRoom(southRoom);
                break;
            case 3:
                int westIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room westRoom = dungeon.getRooms().get(westIndex);
                avatar.setCurrRoom(westRoom);
                break;
            case 4:
                //User has exited the dungeon
                //TODO Do some more stuff!
                avatar.setCurrRoom(null);
                break;
            default:
                break;
        }
    }

}
