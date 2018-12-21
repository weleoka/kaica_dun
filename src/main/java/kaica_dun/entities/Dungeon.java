package kaica_dun.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "dungeon")
public class Dungeon {
    private Long dungeonId;
    private Player player;
    private int roomRows;
    private int roomColumns;
    private List<Room> rooms= new ArrayList<>();
    protected Dungeon() {}

    /**
     * Constructor to recreate dungeon state from database.
     *
     * @param player        the player who owns the dungeon
     * @param roomRows      number of rows in the dungeon matrix
     * @param roomColumns   number of columns in the dungeon matrix
     * @param rooms         a list of all rooms in the dungeon, with null-values for empty spaces in the room-matrix
     */
    public Dungeon(Player player, int roomRows, int roomColumns, List<Room> rooms){

        this.player = player;
        this.roomRows = roomRows;
        this.roomColumns = roomColumns;
        this.rooms = rooms;

    }

    @Id @GeneratedValue
    @Column(name = "dungeonID")
    public Long getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(Long dungeonId) { this.dungeonId = dungeonId; }

    @OneToMany(cascade = { CascadeType.PERSIST,
                           CascadeType.MERGE,
                           CascadeType.REMOVE },
               mappedBy = "dungeon")
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

    //TODO: think about if this(m*n-stuff) is needed or how it can be solved cleaner.
    @Transient
    protected int getRoomRows() {
        return roomRows;
    }

    protected void setRoomRows(int roomRows) {
        this.roomRows = roomRows;
    }

    @Transient
    protected int getRoomColumns() {
        return roomColumns;
    }

    protected void setRoomColumns(int roomColumns) {
        this.roomColumns = roomColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon that = (Dungeon) o;
        return dungeonId == that.dungeonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dungeonId);
    }
}
