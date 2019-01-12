package kaica_dun.entities;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;


/**
 * Class representing a Room.
 *
 * A room has a certain type to identify it in a dungeon and select it for special
 * treatment such as difficulty modifications or added functionality. See enum class
 * for further details.
 *
 * todo: Currently room has multiple List parameters, when fetching data from a
 *  database it will not be in an ordered list, hence Set is recommended for all cases other than where it
 *  is absolutely necessary to maintain order.
 *  https://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags#4335514
 *
 */
@Entity
@Table(name = "Room")
@NamedQuery(name="Room.findFirstRoomInDungeon", query="SELECT MIN(r.id) FROM Room r WHERE r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
@NamedQuery(name="Room.findLastRoomInDungeon", query="SELECT MAX(r.id) FROM Room r WHERE r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
//@NamedQuery(name="Room.findRoomsInDungeonByEnum", query="SELECT r.id FROM Room r WHERE r.roomType LIKE :roomType AND r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
@NamedQuery(name="Room.findRoomsInDungeonByEnum", query="SELECT r.id FROM Room r WHERE r.roomType LIKE :roomType AND r.dungeon LIKE :dungeonId")
@NamedQuery(name="Room.findAliveMonstersInRoom", query="SELECT f.id FROM Fighter f WHERE f.room LIKE :roomId AND f.currHealth > 0")

public class Room {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "roomID", updatable = false, nullable = false)
    private UUID id;

    //Mapping to the dungeon entity that holds the rooms.
    //TODO Fetchtype? Eager or Lazy? More research.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeonID", nullable = false, updatable = false)
    private Dungeon dungeon;

    //@Transient // This can be transient, currently not for development aid.
    @Column(name="room_index")
    private int roomIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType = RoomType.STD01;

    @Transient
    private Direction incomingDoor;

    @Column(name = "room_directionID")
    @ElementCollection(targetClass = Direction.class)
    @CollectionTable(
            name = "Room_direction",
            joinColumns = @JoinColumn(name = "roomID")
    )
    @LazyCollection(LazyCollectionOption.FALSE) // workaround. Should really use Set and not List.
    private List<Direction> directions;


    @OneToMany( //TODO CascadeType.ALL, rework to minimum
            fetch = FetchType.LAZY, // Workaround. Should really use Set and not List.
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE) // Workaround. Should really use Set and not List.
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Monster> monsters;


    // Default empty constructor
    protected Room() {}

    /**
     * The standard construtor for a room in the dungeon existing on a 2D-matrix. Empty room positions are null valued.
     *
     * @param dungeon       the dungeon that the Room belongs to
     * @param roomIndex     the index of the Room in the Dungeon-matrix
     * @param incomingDoor  the direction of the door that leads backwards in the dungeon to the starter room
     * @param directions         a List with the direction(s) of possible directions
     * @param monsters      a List of the monster(s) in the room
     */
    Room(Dungeon dungeon, int roomIndex, Direction incomingDoor, List<Direction> directions, List<Monster> monsters) {
        this.dungeon = dungeon;
        this.roomIndex = roomIndex;
        this.incomingDoor = incomingDoor;
        this.directions = directions;
        this.monsters = monsters;
        this.roomType = RoomType.STD01;
    }

    /**
     * This constructor is used by the tmpRoomMaker to make a List<Room> to be used in dungeon creation later
     *
     * Watch out for invalid states of Rooms without Dungeon references!
     *
     * @param roomIndex     the index of the Room in the Dungeon-matrix
     * @param incomingDoor  the direction of the door that leads backwards in the dungeon to the starter room
     * @param directions         a List with the direction(s) of possible directions
     * @param monsters      a List of the monster(s) in the room
     */
    public Room(int roomIndex, Direction incomingDoor, List<Direction> directions, List<Monster> monsters) {
        //this.dungeon = dungeon;
        this.roomIndex = roomIndex;
        this.incomingDoor = incomingDoor;
        this.directions = directions;
        this.monsters = monsters;
        if (directions != null) {
            this.roomType = RoomType.STD01;
            this.directions.add(Direction.STAY);
        } else {
            this.roomType = RoomType.NULL;
        }

    }


    // ********************** Accessor Methods ********************** //

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID roomId) {
        this.id = roomId;
    }


    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }


    public int getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    /**
     * After randomness some rooms may get a special difficulty, status, message etc etc.
     * and this method sets that. See enum RoomType for details of available types.
     *
     * Manual setting of first and last rooms use this method as well.
     *
     * @param rt            an instance of RoomType enum.
     */
    public void setRoomType(RoomType rt) {
        this.roomType = rt;
    }

    public RoomType getRoomType() { return roomType; }


    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }


    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }


    public Direction getIncomingDoor() {
        return incomingDoor;
    }

    public void setIncomingDoor(Direction incomingDoor) {
        this.incomingDoor = incomingDoor;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Room)) {
            return false;
        }
        Room room = (Room) obj;
        return id != null && id.equals(room.id);
    }

    @Override
    public int hashCode() { return 17; }


    // ********************** Model Methods ********************** //

    //use this method to manage the bidirectional pointers
    public void addMonster(Monster monster) {
        this.monsters.add(monster);
        monster.setRoom(this);
    }

    //use this method to manage the bidirectional pointers
    public void removeMonster(Monster monster) {
        monster.setRoom(null);
        monsters.remove(monster);
    }

    //TODO NOT IN USE!
    //Return an array of possible outgoing door directions, not used currently.
    //TODO: Develop for random dungeon generation.
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
