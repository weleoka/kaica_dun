package kaica_dun.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "dungeon")
public class Dungeon {

    // Field variable declarations and Hibernate annotation scheme
    @Id @GeneratedValue
    @Column(name = "dungeonID")
    private Long dungeonId;

    @ManyToOne
    @JoinColumn(name = "playerID", nullable = false, updatable = false, insertable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_PLAYER_ID")
    private Player player;

    @Transient
    private int roomRows;

    @Transient
    private int roomColumns;

    @OneToMany(cascade = { CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE },
            mappedBy = "dungeon")
    private List<Room> rooms= new ArrayList<>();


    // Default empty constructor
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

    public Long getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(Long dungeonId) { this.dungeonId = dungeonId; }

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

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //TODO: think about if this(m*n-stuff) is needed or how it can be solved cleaner.

    protected int getRoomRows() {
        return roomRows;
    }

    protected void setRoomRows(int roomRows) {
        this.roomRows = roomRows;
    }

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
