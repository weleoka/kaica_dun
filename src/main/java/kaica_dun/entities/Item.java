package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Representing items that can be picked up and worn/used by an Avatar.
 *
 * Current implementation allows equipping a weapon to the armor slot and an armor to the weapon slot. This is bad.
 * TODO move stuff out to ItemConsumable
 */
@Entity
@Table(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_discriminator")
public class Item implements Describable {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "itemID", updatable = false, nullable = false)
    private UUID id;

    @Basic
    @Column(name = "item_name")
    private String itemName;

    @Basic
    @Column(name = "description")
    private String description;

    //points to the inventory of an avatar if the item is located there. TODO possibly remove this pointer and make unidirectional
    @ManyToOne
    @JoinColumn(name = "inventoryID", nullable = true, updatable = true)
    private Inventory inventory;


    // Default empty constructor
    protected Item() {}

    public Item(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }


    // ********************** Accessor Methods ********************** //

    public UUID getItemId() {
        return id;
    }

    public void setItemId(UUID itemId) {
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
                Objects.equals(description, that.description);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, description);
    }
}
