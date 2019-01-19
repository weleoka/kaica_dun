package kaica_dun.entities;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
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
//@NamedQuery(name="Room.findFirstRoomInDungeon", query="SELECT MIN(r.id) FROM Room r WHERE r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
//@NamedQuery(name="Room.findLastRoomInDungeon", query="SELECT MAX(r.id) FROM Room r WHERE r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
//@NamedQuery(name="Room.findRoomsInDungeonByEnum", query="SELECT r.id FROM Room r WHERE r.roomType LIKE :roomType AND r.dungeon LIKE :dungeonId GROUP BY r.dungeon")
//@NamedQuery(name="Room.findRoomsInDungeonByEnum", query="SELECT r.id FROM Room r WHERE r.roomType LIKE :roomType AND r.dungeon LIKE :dungeonId")
//@NamedQuery(name="Room.findAliveMonstersInRoom", query="SELECT f.id FROM Fighter f WHERE f.room LIKE :roomId AND f.currHealth > 0")

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

    /**
    //Mapping to the dungeon entity that holds the rooms.
    //TODO Fetchtype? Eager or Lazy? More research.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeonID", nullable = false, updatable = false)
    private Dungeon dungeon;
    **/

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
    private Set<Direction> directions = new LinkedHashSet<Direction>();


    @OneToMany( //TODO CascadeType.ALL, rework to minimum
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)   //TODO Also potential workaround, might need to remove
    @Fetch(FetchMode.JOIN)
    private Set<Monster> monsters = new LinkedHashSet<Monster>();

    @OneToMany( //TODO CascadeType.ALL, rework to minimum
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)   //TODO Also potential workaround, might need to remove
    @Fetch(FetchMode.JOIN)
    private Set<Chest> chests = new LinkedHashSet<>();


    // Default empty constructor
    protected Room() {}

    /**
     * This constructor is used by the tmpRoomMaker to make a List<Room> to be used in dungeon creation later
     *
     *
     * @param roomIndex     the index of the Room in the Dungeon-matrix
     * @param incomingDoor  the direction of the door that leads backwards in the dungeon to the starter room
     * @param directions         a List with the direction(s) of possible directions
     * @param monsters      a List of the monster(s) in the room
     */
    public Room(int roomIndex, Direction incomingDoor, Set<Direction> directions, Set<Monster> monsters, Set<Chest> chests) {
        //this.dungeon = dungeon;
        this.roomIndex = roomIndex;
        this.incomingDoor = incomingDoor;
        this.directions = directions;
        //This is an O(n) operation.
        for(Monster m : monsters) { this.monsters.add(m); }
        if (directions != null) {
            this.roomType = RoomType.STD01;
            this.directions.add(Direction.STAY);
        } else {
            this.roomType = RoomType.NULL;
        }
        if(chests != null) {
            for(Chest c : chests) { this.chests.add(c); }
        }

    }


    // ********************** Accessor Methods ********************** //

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID roomId) {
        this.id = roomId;
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

    public void addChest() {}

    public RoomType getRoomType() { return roomType; }

    public Set<Chest> getChests() { return chests; }

    public void setChests(LinkedHashSet<Chest> chests) { this.chests = chests; }

    public Set<Monster> getMonsters() { return monsters; }
    //TODO unsure if this need to be Set or LinkedHashSet
    public void setMonsters(LinkedHashSet<Monster> monsters) { this.monsters = monsters; }


    public Set<Direction> getDirections() {
        return directions;
    }

    public void setDirections(LinkedHashSet<Direction> directions) {
        this.directions = directions;
    }


    public Direction getIncomingDoor() {
        return incomingDoor;
    }

    public void setIncomingDoor(Direction incomingDoor) {
        this.incomingDoor = incomingDoor;
    }


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


    // ********************** Common Methods ********************** //

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
    public int hashCode() { return Objects.hash(id); }
}
