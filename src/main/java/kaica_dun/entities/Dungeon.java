package kaica_dun.entities;

import kaica_dun_system.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Dungeon")
public class Dungeon {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dungeonID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", updatable = false, nullable = false)
    private User user;

    @Basic
    @Column(name = "room_rows")
    private int roomRows;

    @Basic
    @Column(name = "room_columns")
    private int roomColumns;

    @OneToMany(mappedBy = "dungeon", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<Room>();


    // Default empty constructor
    protected Dungeon() {}



    /**
     * Constructor to recreate dungeon state from database.
     *
     * @param user        the user who owns the dungeon
     * @param roomRows      number of rows in the dungeon matrix
     * @param roomColumns   number of columns in the dungeon matrix
     * @param rooms         a list of all rooms in the dungeon, with null-values for empty spaces in the room-matrix
     */
    public Dungeon(User user, int roomRows, int roomColumns, List<Room> rooms){
        this.user = user;
        this.roomRows = roomRows;
        this.roomColumns = roomColumns;
        this.rooms = rooms;
    }

    public Long getDungeonId() {
        return this.id;
    }

    public void setDungeonId(Long dungeonId) { this.id = dungeonId; }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        room.setDungeon(this);
        rooms.add(room);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon that = (Dungeon) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
