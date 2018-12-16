package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "kaicadungeon")
public class Room {
    private int roomId;
    private ArrayList<Monster> monsters = new ArrayList<>();

    @Id
    @Column(name = "room_id")
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
}
