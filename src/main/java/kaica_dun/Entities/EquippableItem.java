package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equippableitem", schema = "kaicadungeon")
public class EquippableItem {
    private int itemId;
    private int slotId;
    private int armor;
    private int damage;

    @Id
    @Column(name = "itemID")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "slotID")
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    @Basic
    @Column(name = "armor")
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Basic
    @Column(name = "damage")
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquippableItem that = (EquippableItem) o;
        return itemId == that.itemId &&
                slotId == that.slotId &&
                armor == that.armor &&
                damage == that.damage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, slotId, armor, damage);
    }
}
