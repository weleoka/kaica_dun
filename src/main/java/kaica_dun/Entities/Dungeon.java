package main.java.kaica_dun.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "dungeon", schema = "kaicadungeon")
public class Dungeon {
    private int dungeonId;

    Dungeon(int m, int n){

    }

    @Id
    @Column(name = "dungeonID")
    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; }

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
