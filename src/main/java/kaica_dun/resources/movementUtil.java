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
    }

    private static void moveAvatar(Avatar avatar, Dungeon dungeon, Direction direction) {
        int currRoomIndex = avatar.getCurrRoom().getRoomIndex();
        Dungeon currDungeon = avatar.getCurrDungeon();
        int directionNum = direction.ordinal();

        switch (directionNum) {
            case 0:
                //TODO simplify logic.
                int rIndex = avatar.getCurrDungeon().getNorthIndex(currRoomIndex);
                Room r = dungeon.getRooms().get(rIndex);
                avatar.setCurrRoom(r);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}
