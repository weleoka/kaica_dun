package kaica_dun.resources;

import kaica_dun.entities.Direction;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import kaica_dun_system.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Trying something smarter to make the dungeon, and separating that from the persistence mechanism
 */
public class tmpDungeonMaker {
    private User user;
    private int roomRows;
    private int roomColumns;
    private int maxRooms;
    private int numRooms = 0;
    private Random rand = new Random();

    /**
     * Create a new dungeon, belonging to a user, that we can then save to the db.
     *
     * This class constructs values/datastructures for the parameters of the Dungeon-constructor,
     * which it then calls using those values/datastructures as parameters.
     *
     * @param user        the User that the Dungeon belongs to
     * @param roomRows      the number of Room rows in the dungeon matrix
     * @param roomColumns   the number of Room columns in the dungeon matrix
     * @param maxRooms      max number of Rooms in the dungeon, including the start and boss room, excluding null rooms
     */
    public tmpDungeonMaker(User user, int roomRows, int roomColumns, int maxRooms){
        this.user = user;
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
        numRooms++;

        //Make all the other rooms TODO EVERYTHING! Atm the dungeon is only the entrance room.
        while (numRooms < maxRooms) {
            Room prevRoom = rooms.get(prevRoomIndex);        //Fetch the previous room
            List<Direction> incoming = prevRoom.getExits();  //Fetch previously created room's exit list
            nextRoomIndex = -1;                              //Index of next room to be created, -1 sentinel
            for (Direction d : incoming) {
                int directionInt = d.getDirectionNumber();
                //create a list of the indexes for the possible rooms
                LinkedList<Integer> nextRoomIndexes = new LinkedList<Integer>();
                if ((getPossibleRoomIndex(directionInt, prevRoomIndex)) >= 0 && (getPossibleRoomIndex(directionInt, prevRoomIndex)) < 5) {
                    nextRoomIndexes.add(getPossibleRoomIndex(directionInt, prevRoomIndex));
                }
                //random, legal, direction for the next room

                //random, legal, index for the next room
                nextRoomIndex = nextRoomIndexes.get(rand.nextInt(nextRoomIndexes.size()));
            }
            //TODO PLACEHOLDER DIRECTION!
            //TODO Need to rethink how the room creation keeps track of the entrances and exits
            rooms.set(nextRoomIndex, tmpRoomMaker.makeRoom(prevRoom, nextRoomIndex, Direction.N));

        }


        //Make boss room

        //make a Dungeon using the saved parameters
        //TODO Think about this, the Dungeon exists in an invalid state here until the Dungeon references of its Rooms
        // are updated.
        Dungeon newDungeon = new Dungeon(user, roomRows, roomColumns, rooms);

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

    /**
     * Get a list of all possible room indexes to make the next room.
     */
    private int getPossibleRoomIndex(int directionInt, int roomIndex) {
        //TODO this is not clean, possible refactor
        int possibleRoomIndex = -1; //-1 sentinel

        if ((directionInt == 0) && (hasNorth(roomIndex))) {
            possibleRoomIndex = getNorth(roomIndex);
        } else if ((directionInt == 1) && (hasEast(roomIndex))) {
            possibleRoomIndex = getEast(roomIndex);
        } else if ((directionInt == 2) && (hasSouth(roomIndex))) {
            possibleRoomIndex = getSouth(roomIndex);
        } else if ((directionInt == 3) && (hasWest(roomIndex))) {
            possibleRoomIndex = getWest(roomIndex);
        }

        return possibleRoomIndex;
    }
}