package kaica_dun.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room {
    private Direction incomingDoor;
    private int roomId;
    private Dungeon dungeon;
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Room(){}

    //TODO: Keep working here!
    Room(boolean isStarter) {

    }
    Room(Direction incomingDoor) {

    }



    @Id
    @Column(name = "roomID")
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room that = (Room) o;
        return roomId == that.roomId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    //Mapping to the dungeon entity that holds the rooms.
    @ManyToOne
    @JoinColumn(name = "dungeonID", nullable = false, updatable = false)
    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    @Transient
    public Direction getIncomingDoor() {
        return incomingDoor;
    }

    public void setIncomingDoor(Direction incomingDoor) {
        this.incomingDoor = incomingDoor;
    }

    //Return an array of possible outgoing door directions
    //TODO: Possible refactor. This method has a thousand and one possible implementations, dunno which is the best.
    private Direction[] legalDirections(Direction incomingDoor) {
        boolean[] possibleDirections = new boolean[4];  //possibly replace with Direction[] from the start?
        Direction[] dir = new Direction[10];            //TODO PLACEHOLDER
        //Check 1: remove incoming direction. Store possible outgoing directions as true in the boolean array.
        for (int i = 0; i < 4 ; i++) {

            if (incomingDoor.getDirectionNumber() != i) {
                possibleDirections[i] = true;
            }

        }

        //check for walls
        dungeon.getRooms().get(0);


        //check for existing rooms

        return dir;
    }

    /*
    Make sure the dungeon has a possible position to the correct direction
    Below code is only for position inside the room matrix, not for checking against other rooms
    TODO use in method that also checks for already created rooms
    TODO use switch to allow a direction input instead/as well? Think about logic.
    */
    private boolean hasNorth(int roomPosition) {
        boolean hasNorth = false;
        if ((roomPosition + 1) > dungeon.getRoomColumns()) { hasNorth = true; }
        return hasNorth;
    }

    private boolean hasEast(int roomPosition) {
        boolean hasEast = false;
        if ((roomPosition + 1) % (dungeon.getRoomColumns()) != 0) {
            hasEast = true;
        }
        return hasEast;
    }

    private boolean hasSouth(int roomPosition) {
        boolean hasSouth = false;
        int numRooms = dungeon.getRoomColumns() * dungeon.getRoomRows();
        if (roomPosition < numRooms - dungeon.getRoomColumns()) {
            hasSouth = true;
        }
        return hasSouth;
    }

    private boolean hasWest(int roomPosition) {
        boolean hasWest = false;
        if (roomPosition % dungeon.getRoomColumns() != 0) {
            hasWest = true;
        }
        return hasWest;
    }

}
