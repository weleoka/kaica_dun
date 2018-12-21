package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equippableitem")
public class EquippableItem {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equippableItemID", updatable = false, nullable = false)
    private Long id;

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
        return id;
    }

    public void setItemId(Long itemId) {
        this.id = itemId;
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
        return id.equals(that.id) &&
                slotId == that.slotId &&
                armor == that.armor &&
                damage == that.damage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slotId, armor, damage);
    }
}
