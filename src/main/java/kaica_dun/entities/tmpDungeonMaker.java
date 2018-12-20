package kaica_dun.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Trying something smarter to make the dungeon, and separating that from the persistence mechanism
 */
public class tmpDungeonMaker {
    private Player player;
    private int roomRows;
    private int roomColumns;
    private int maxRooms;
    private int numRooms = 0;

    /**
     * Create a new dungeon, belonging to a player, that we can then save to the db.
     *
     * This class constructs values/datastructures for the parameters of the Dungeon-constructor,
     * which it then calls using those values/datastructures as parameters.
     *
     * @param player        the Player that the Dungeon belongs to
     * @param roomRows      the number of Room rows in the dungeon matrix
     * @param roomColumns   the number of Room columns in the dungeon matrix
     * @param maxRooms      max number of Rooms in the dungeon, including the start and boss room, excluding null rooms
     */
    public tmpDungeonMaker(Player player, int roomRows, int roomColumns, int maxRooms){
        this.player = player;
        this.roomRows = roomRows;
        this.roomColumns = roomColumns;
        this.maxRooms = maxRooms;
    }

    private Dungeon makeDungeon() {
        List<Room> rooms = new ArrayList<Room>();
        //loop for room-list creation, initialised to null.
        for (int i = 0; i < (roomRows * roomColumns); i++ ) {
            rooms.add(null);
        }
        int prevRoomIndex;
        int nextRoomIndex;
        //Make starter room
        rooms.set(0, tmpRoomMaker.makeStarterRoom());
        prevRoomIndex = 0;
        nextRoomIndex = rooms.get(prevRoomIndex).;
        numRooms++;

        //Make all the other rooms TODO EVERYTHING! Atm the dungeon is only the entrance room.
        while (numRooms < maxRooms) {

        }


        //Make boss room

        //make a Dungeon using the saved parameters
        //TODO Think about this, the Dungeon exists in an invalid state here until the Dungeon references of its Rooms
        // are updated.
        Dungeon newDungeon = new Dungeon(player, roomRows, roomColumns, rooms);

        //update the dungeon reference in the rooms of the new dungeon
        for (Room r : newDungeon.getRooms()) {
            if (r != null) {
                r.setDungeon(newDungeon);
            }
        }

        return newDungeon;
    }

    /**
    Make sure the dungeon has a possible position to the correct direction
    Below code is only for position inside the room matrix, not for checking against other rooms
    TODO use in method that also checks for already created rooms
    TODO use switch to allow a direction input instead/as well? Think about logic.
    */
    private boolean hasNorth(int roomPosition) {
        boolean hasNorth = false;
        if ((roomPosition + 1) > roomColumns) { hasNorth = true; }
        return hasNorth;
    }

    private boolean hasEast(int roomPosition) {
        boolean hasEast = false;
        if ((roomPosition + 1) % roomColumns != 0) {
            hasEast = true;
        }
        return hasEast;
    }

    private boolean hasSouth(int roomPosition) {
        boolean hasSouth = false;
        int numRooms = roomColumns * roomRows;
        if (roomPosition < numRooms - roomColumns) {
            hasSouth = true;
        }
        return hasSouth;
    }

    private boolean hasWest(int roomPosition) {
        boolean hasWest = false;
        if (roomPosition % roomColumns != 0) {
            hasWest = true;
        }
        return hasWest;
    }

    /**
     * Check hasNorth/E/S/W first or this might throw an IndexOutOfBoundsException
     *
     * TODO handle errors
     * TODO refactor with switch statement to take direction and position?
     */
    private int getNorth(int roomPosition) {
        return roomPosition - roomColumns;
    }

    private int getEast(int roomPosition) {
        return roomPosition + 1;
    }

    private int getSouth(int roomPosition) {
        return roomPosition + roomColumns;
    }

    private int getWest(int roomPosition) {
        return roomPosition - 1;
    }

}