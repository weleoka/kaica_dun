package kaica_dun.entities;

import kaica_dun_system.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Dungeon")
public class Dungeon {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @Type(type="uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "dungeonID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userID", updatable = true, nullable = true)
    private User user;

    @Basic
    @Column(name = "room_rows")
    private int roomRows;

    @Basic
    @Column(name = "room_columns")
    private int roomColumns;

    @OneToMany(
            mappedBy = "dungeon",
            cascade = CascadeType.ALL
    )
    @LazyCollection(LazyCollectionOption.FALSE) // workaround. Should really use Set and not List.
    private List<Room> rooms = new ArrayList<Room>();


    // Default empty constructor
    protected Dungeon() {}



    /**
     * Full Constructor.
     *
     * @param roomRows      number of rows in the dungeon matrix
     * @param roomColumns   number of columns in the dungeon matrix
     * @param rooms         a list of all rooms in the dungeon, with null-values for empty spaces in the room-matrix
     */
    public Dungeon(int roomRows, int roomColumns, List<Room> rooms){
        this.roomRows = roomRows;
        this.roomColumns = roomColumns;
        this.rooms = rooms;
    }


    // ********************** Accessor Methods ********************** //

    public User getUser(){
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //TODO: think about if this(m*n-stuff) is needed or how it can be solved cleaner.

    protected int getRoomRows() {
        return this.roomRows;
    }

    protected void setRoomRows(int roomRows) {
        this.roomRows = roomRows;
    }

    protected int getRoomColumns() {
        return this.roomColumns;
    }

    protected void setRoomColumns(int roomColumns) {
        this.roomColumns = roomColumns;
    }

    public UUID getDungeonId() { return this.id; }

    public void setDungeonId(UUID dungeonId) { this.id = dungeonId; }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }


    // ********************** Model Methods ********************** //

    public void addRoom(Room room) {
        room.setDungeon(this);
        rooms.add(room);
    }

    public int getNorthIndex(int currRoomIndex) {
        int northIndex = currRoomIndex - roomColumns;
        return northIndex;
    }

    //The has[Direction]Index methods are not really needed atm since you can only exit through legal doors.
    public boolean hasNorthIndex(int currRoomIndex) {
        return ((currRoomIndex - roomColumns) >= 0);
    }

    public int getEastIndex(int currRoomIndex) {
        int eastIndex = currRoomIndex + 1;
        return eastIndex;
    }

    public boolean hasEastIndex(int currRoomIndex) {
        return (((currRoomIndex + 1) % roomColumns) != 0);
    }

    public int getWestIndex(int currRoomIndex) {
        int westIndex = currRoomIndex - 1;
        return westIndex;
    }

    public boolean hasWestIndex(int currRoomIndex) {
        return ((currRoomIndex % roomColumns) != 0);
    }

    public int getSouthIndex(int currRoomIndex) {
        int southIndex = currRoomIndex + roomColumns;
        return southIndex;
    }

    public boolean hasSouthIndex(int currRoomIndex) {
        return ((currRoomIndex + roomColumns) < (roomColumns * roomRows));
    }

    //TODO print more information about the rooms, like ID for example?
    public void printRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            System.out.printf(i + ": " + rooms.get(i) + "\n");
        }
    }



    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Dungeon)) {
            return false;
        }
        Dungeon dungeon = (Dungeon) obj;
        return id != null && id.equals(dungeon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
