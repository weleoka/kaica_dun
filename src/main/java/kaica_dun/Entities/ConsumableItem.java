package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "consumableitem", schema = "kaicadungeon")
public class ConsumableItem {
    private int itemId;
    private int uses;

    @Id
    @Column(name = "itemID")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "uses")
    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumableItem that = (ConsumableItem) o;
        return itemId == that.itemId &&
                uses == that.uses;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, uses);
    }
}
