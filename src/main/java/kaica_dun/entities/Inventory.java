package kaica_dun.entities;

import javax.persistence.*;
import java.util.List;

/**
 * The inventory of an Avatar.
 *
 * An instance of this class is always associated with only
 * one <tt>Avatar</tt> and depends on that parent objects lifecycle,
 * it is a component. Monsters might also have an inventory in a future version.
 *
 * Based on the Hibernate reference implementation "CaveatEmptor.JPA"
 *
 * @see Avatar
 */
@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(generator = "avatarInventorySharedPKGenerator")
    @Column(name = "inventory_id")
    private Long id = null;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Avatar owner;

    @Basic
    @Column(name= "max_size")
    private int maxSize;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL) // Kai: Changed from "avatar_inventory"
    private List<Item> items;

    public Inventory() {}

    public Inventory(int maxSize, List<Item> items) {
        this.maxSize = maxSize;
        this.items = items;
    }


    // ********************** Accessor Methods ********************** //

    public int getMaxSize() { return maxSize; }

    public void setMaxSize(int maxSize) { this.maxSize = maxSize; }

    public List<Item> getItems() { return items; }

    public void setItems(List<Item> items) { this.items = items; }

    //use this method to manage the bidirectional pointers
    public void addItem(Item item) {
        items.add(item);
        item.setInventory(this);
    }

    //use this method to manage the bidirectional pointers
    public void removeItem(Item item) {
        item.setInventory(null);
        items.remove(item);
    }

    // ********************** Model Methods ********************** //



    // ********************** Common Methods ********************** //
}
