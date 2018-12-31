package kaica_dun.entities;

import kaica_dun.interfaces.Describable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representing items that can be picked up and worn/used by an Avatar.
 *
 * Current implementation allows equipping a weapon to the armor slot and an armor to the weapon slot. This is bad.
 * TODO move stuff out to ItemEquippable->Weapon/Armor and ItemConsumable
 */
@Entity
@Table(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_discriminator")
public class Item implements Describable {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "item_name")
    private String itemName;

    @Basic
    @Column(name = "description")
    private String description;

    //points to the inventory of an avatar if the item is located there
    @ManyToOne
    @JoinColumn(name = "fighterID", nullable = true, updatable = true)
    private Inventory inventory;

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "low_damage")
    private int lowDamage;

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "damage_range")
    private int damageRange;

    @OneToOne(optional = true)
    @JoinColumn(name = "wielderID")
    private Avatar wielder;


    // Default empty constructor
    public Item(){}

    public Item(String itemName, String description, int lowDamage, int damageRange) {
        this.itemName = itemName;
        this.description = description;
        this.lowDamage = lowDamage;
        this.damageRange = damageRange;
    }


    // ********************** Accessor Methods ********************** //

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

    public Inventory getInventory() { return this.inventory; }

    public void setInventory(Inventory inventory) { this.inventory = inventory; }


    // ********************** Common Methods ********************** //

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
                Objects.equals(wielder, that.wielder);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, description, lowDamage, damageRange, wielder);
    }
}
