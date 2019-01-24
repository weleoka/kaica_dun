package kaica_dun.resources;

import kaica_dun.entities.Direction;
import kaica_dun.entities.Monster;
import kaica_dun.entities.Room;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@Component
public class tmpRoomMaker {

    protected tmpRoomMaker() { }



    /**
     * Create the first room in the dungeon in the top-left corner.
     * @return the special-case starter room
     */
    protected static Room makeStarterRoom() {
        Direction dungeonExit = Direction.U;                //leading out of the dungeon
        Set<Direction> exits = new LinkedHashSet<Direction>();         //create List for directions leading out of the room.
        Set<Monster> monsters = new LinkedHashSet<>();        //make no monsters in the starter room, empty monster list.
        exits.add(Direction.S);                          //always exit the starter room to the south

        return new Room(0, dungeonExit, exits, monsters, null);   //create the room using the params created above
    }

    /**
     * Create a randomized Room in the Dungeon.
     *
     * @return a normal room
     */
    protected static Room makeRoom(Room prevRoom, int roomIndex, Direction incomingDoor){
        Random rGen = new Random();

        Set<Direction> exits = new LinkedHashSet<Direction>();         //create List for directions leading out of the room.
        Set<Monster> monsters = new LinkedHashSet<Monster>();        //make monsters-list to be populated
        exits.add(Direction.E);                          //set exit direction, TODO PH

        return new Room(0, incomingDoor, exits, monsters, null);   //create the room using the params created above
    }
}
