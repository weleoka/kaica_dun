package kaica_dun.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "dungeon")
public class Dungeon {
    private Player player;
    private int dungeonId;
    private int roomRows;
    private int roomColumns;
    private List<Room> rooms= new ArrayList<>();
    protected Dungeon() {}

    public Dungeon(Player player, int roomRows, int roomColumns, int numRooms, int maxRooms){

        //TODO: think about auto-generating strategy for dungeonID
        this.player = player;
        this.roomRows = roomRows;
        this.roomColumns = roomColumns;

        //loop for room-list creation, initialised to null.
        for (int i = 0; i < (roomRows * roomColumns); i++ ) {
            rooms.add(null);
        }

        //TODO: Figure out all the logic of how to control which kind of room gets created
        rooms.set(0, new Room(true));

    }

    @Id
    @Column(name = "dungeonID")
    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; }

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
