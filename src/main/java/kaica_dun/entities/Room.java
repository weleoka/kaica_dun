package kaica_dun.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    private Dungeon dungeon;
    private int roomIndex;
    private Direction incomingDoor;
    private Set<Direction> exits;
    private Long roomId;
    private List<Monster> monsters = new ArrayList<>();

    public Room(){}

    /**
     * @param dungeon       the dungeon that the Room belongs to
     * @param roomIndex     the index of the Room in the Dungeon-matrix
     * @param incomingDoor  the direction of the door that leads backwards in the dungeon to the starter room
     * @param exits         a List with the direction(s) of possible exits
     * @param monsters      a List of the monster(s) in the room
     */
    Room(Dungeon dungeon, int roomIndex, Direction incomingDoor, Set<Direction> exits, List<Monster> monsters) {
        this.dungeon = dungeon;
        this.roomIndex = roomIndex;
        this.incomingDoor = incomingDoor;
        this.exits = exits;
        this.monsters = monsters;
    }

    /**
     * This constructor is used by the tmpRoomMaker to make a List<Room> to be used in dungeon creation later
     *
     * Watch out for invalid states of Rooms without Dungeon references!
     *
     * @param roomIndex     the index of the Room in the Dungeon-matrix
     * @param incomingDoor  the direction of the door that leads backwards in the dungeon to the starter room
     * @param exits         a List with the direction(s) of possible exits
     * @param monsters      a List of the monster(s) in the room
     */
    Room(int roomIndex, Direction incomingDoor, Set<Direction> exits, List<Monster> monsters) {
        this.dungeon = dungeon;
        this.roomIndex = roomIndex;
        this.incomingDoor = incomingDoor;
        this.exits = exits;
        this.monsters = monsters;
    }


    @Id @GeneratedValue
    @Column(name = "roomID")
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
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

    //TODO unsure of correct annotation here
    public int getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    @ElementCollection(targetClass = Direction.class)
    @CollectionTable(
            name = "room_direction",
            joinColumns = @JoinColumn(name = "roomID"))
    @Column(name = "directionID")
    public Set<Direction> getExits() {
        return exits; // TODO: Why not this.exits?
    }

    public void setExits(Set<Direction> exits) {
        this.exits = exits;
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
    private Direction[] legalDirections() {

        boolean[] possibleDirections = new boolean[4];  //possibly replace with Direction[] from the start?
        Direction[] dir = new Direction[10];            //TODO PLACEHOLDER
        //Check 1: remove incoming direction. Store possible outgoing directions as true in the boolean array.
        for (int i = 0; i < 4 ; i++) {

            if (this.incomingDoor.getDirectionNumber() != i) {
                possibleDirections[i] = true;
            }

        }

        //check for walls
        dungeon.getRooms().get(0);


        //check for existing rooms

        return dir;                                     //TODO PH
    }
}
