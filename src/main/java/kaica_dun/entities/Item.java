package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
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

    @Id
    @GeneratedValue
    @Column(name = "itemID", updatable = false, nullable = false)
    protected Long id;

    @NaturalId
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @Column(nullable = false, unique = true)
    protected UUID uuid = UUID.randomUUID();

    //TODO optional = true for testing, might want it set to false later
    @ManyToOne(optional = true)
    @JoinColumn(name = "containerID")
    private Container containedIn;

    @Basic
    @Column(name = "item_name")
    private String itemName;

    @Basic
    @Column(name = "description")
    private String description;

    // Default empty constructor
    protected Item() {}

    //TODO temporary while reworking equipment
    public Item(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }

    /**
     * Full constructor.
     * @param itemName      the name of the item
     * @param description   the item's description
     * @param container     the {@code Container} that the item is to be held in when created
     */
    public Item(String itemName, String description, Container container) {
        this.itemName = itemName;
        this.description = description;
        this.containedIn = container;
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

    public void setContainedIn(Container containedIn) { this.containedIn = containedIn; }

    public Container getContainedIn() { return this.containedIn; }


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Item)) {
            return false;
        }
        Item item = (Item) obj;
        return uuid != null && uuid.equals(item.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
