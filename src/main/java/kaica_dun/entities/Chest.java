package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * A container that is Lootable. It is also describable.
 */
@Entity
@Table(name = "Chest")
public class Chest implements Describable, Lootable {

    @Id
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "chestID", updatable = false, nullable = false)
    protected UUID id;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @PrimaryKeyJoinColumn
    private LootContainer loot = new LootContainer();

    @ManyToOne
    @JoinColumn(name = "roomID", nullable = true, updatable = false)
    private Room room;

    protected Chest() {}

    public Chest(int rewardLevel) {
        this.loot = new LootContainer();
    }

    public Chest(boolean startChest) {
        this.loot = new LootContainer(true);
    }

    // ********************** Accessor Methods ********************** //


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LootContainer getContainer() {
        return loot;
    }

    public void setLoot(LootContainer loot) {
        this.loot = loot;
    }

    // ********************** Model Methods ********************** //

    /**
     * Take one item from the chest.
     *
     * //TODO take one item from the chest by indexing into a specific location containing an item.
     *
     * @param inventory     the {@code Inventory} which is to have the {@code Item} added to it
     * @return              the {@code Item} that was looted, or {@code null} if no {@code Item} is exists in the chest
     */
    public Item lootNextItem(Inventory inventory) {
        //Updates the item pointers in both of the containers and returns a reference to the moved item.
        //TODO possibly update the Item.container reference as well. If bidirectional.
        return loot.moveNextItem(inventory);
    }

    /**
     * Take all items from the chest.
     *
     * @param inventory     the {@code Inventory} which is to have the {@code Item}s added to it
     * @return              a {@code LinkedList} of the objects to be looted, or {@code null} if no
     *                      {@code Item}s are returned
     */
    public List<Item> lootAll(Inventory inventory) {
        //Updates the item pointers in both of the containers and returns a reference to the moved item.
        //TODO possibly update the Item.container reference as well. If bidirectional.
        return loot.moveAll(inventory);
    }

    /**
     * Look at the chest.
     *
     * @return
     */
    public String getDescription() {
        return String.format("A chest, it looks unlocked.");
    }

    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Chest)) {
            return false;
        }
        Chest chest = (Chest) obj;
        return id != null && id.equals(chest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
