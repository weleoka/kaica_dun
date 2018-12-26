package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representing items that can be picked up and worn/used by an Avatar.
 *
 * Current implementation allows equipping a weapon to the armor slot and an armor to the weapon slot. This is bad.
 * TODO move stuff out to ItemEquippable->Weapon/Armor and ItemConsumable
 */
@Entity
@Table(name = "item")
public class Item {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "itemName")
    private String itemName;

    @Basic
    @Column(name = "description")
    private String description;

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "low_damage")
    private int lowDamage;

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "damage_range")
    private int damageRange;

    //TODO move out to Armor once Item inheritance is up and running!
    @Basic
    @Column(name = "armor_value")
    private int armorValue;

    @OneToOne(optional = true)
    @JoinColumn(name = "wielderID")
    private Avatar wielder;

    @OneToOne(optional = true)
    @JoinColumn(name = "wearerID")
    private Avatar wearer;

    // Default empty constructor
    public Item(){}

    public Item(String itemName, String description, int lowDamage, int damageRange, int armorValue) {
        this.itemName = itemName;
        this.description = description;
        this.lowDamage = lowDamage;
        this.damageRange = damageRange;
        this.armorValue = armorValue;
    }

    public Long getItemId() {
        return id;
    }

    public void setItemId(Long itemId) {
        this.id = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public int getLowDamage() { return lowDamage; }

    public void setLowDamage(int lowDamage) { this.lowDamage = lowDamage; }

    public int getDamageRange() { return damageRange; }

    public void setDamageRange(int damageRange) { this.damageRange = damageRange; }

    public int getArmorValue() { return armorValue; }

    public void setArmorValue(int armorValue) { this.armorValue = armorValue; }

    public Avatar getWielder() {
        return this.wielder;
    }

    public void setWielder(Avatar wielder) {
        this.wielder = wielder;
    }

    public Avatar getWearer() { return this.wearer; }

    public void setWearer(Avatar wearer) { this.wearer = wearer; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return id.equals(that.id) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(description, that.description) &&
                lowDamage == that.lowDamage &&
                damageRange == that.damageRange &&
                Objects.equals(wielder, that.wielder) &&
                Objects.equals(wearer, that.wearer);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, description, lowDamage, damageRange, wielder, wearer);
    }
}
