package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equippableitem")
public class EquippableItem {

    // Field variable declarations and Hibernate annotation scheme
    @Id @GeneratedValue
    @Column(name = "itemID")
    private Long itemId;

    @Basic
    @Column(name = "slotID")
    private int slotId;

    @Basic
    @Column(name = "armor")
    private int armor;

    @Basic
    @Column(name = "damage")
    private int damage;


    // Default empty constructor
    public EquippableItem(){}


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

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
