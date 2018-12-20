package kaica_dun.entities;

import java.util.*;

public class tmpRoomMaker {

    tmpRoomMaker() { }



    /**
     * Create the first room in the dungeon in the top-left corner.
     * @return the special-case starter room
     */
    protected static Room makeStarterRoom() {
        Direction dungeonExit = Direction.U;                //leading out of the dungeon
        List<Direction> exits = new ArrayList<Direction>(); //create List for directions leading out of the room.
        List<Monster> monsters = new ArrayList<Monster>();  //make no monsters in the starter room, empty monster list.
        exits.set(0, Direction.S);                          //always exit the starter room to the south

        return new Room(0, dungeonExit, exits, monsters);   //create the room using the params created above
    }

    /**
     * Create a randomized Room in the Dungeon.
     *
     * @return a normal room
     */
    protected static Room makeRoom(Room prevRoom, int roomIndex, Direction incomingDoor){
        Random rGen = new Random();

        List<Direction> exits = new ArrayList<Direction>(); //create List for directions leading out of the room.
        List<Monster> monsters = new ArrayList<Monster>();  //make no monsters in the starter room, empty monster list.

        exits.set(0, Direction.S);                          //always exit the starter room to the south

        return new Room(0, Direction.N, exits, monsters);   //create the room using the params created above
    }
}
