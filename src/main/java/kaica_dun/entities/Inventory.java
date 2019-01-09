package kaica_dun.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "inventoryID")
    private UUID id;

    @OneToOne(optional = true) // todo: change to non-optional
    private Avatar owner;

    @Basic
    @Column(name = "max_size")
    private int maxSize;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Item> items;

    //Default no-args constructor
    protected Inventory() {}

    /**
     * Full constructor.
     *
     * @param avatar the avatar to which the inventory belongs
     */
    protected Inventory(Avatar avatar) {
        this.maxSize = 20;
        this.owner = avatar;
        this.items = new ArrayList<Item>(maxSize);
    }


    // ********************** Accessor Methods ********************** //

    public int getMaxSize() { return maxSize; }

    public void setMaxSize(int maxSize) { this.maxSize = maxSize; }

    public List<Item> getItems() { return items; }

    public void setItems(List<Item> items) { this.items = items; }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }



    // ********************** Model Methods ********************** //

    //use this method to manage the bidirectional pointers
    public void addItem(Item item) {
        items.add(item);
        item.setInventory(this);
        //Remove the bidirectional pointers between the item and any Armor/Weapon and the Avatar.
        if (item.getClass() == Weapon.class) {
            if (((Weapon)item).getWielder() == null){
                ((Weapon)item).getWielder().unEquippWeapon();
            }
        } else if (item.getClass() == Armor.class) {
            ((Armor)item).getWearer().unEquippArmor();
        }
    }

    //use this method to manage the bidirectional pointers
    public void removeItem(Item item) {
        item.setInventory(null);
        items.remove(item);
    }


    // ********************** Common Methods ********************** //
}
