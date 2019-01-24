package kaica_dun.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * The inventory of an Avatar.
 *
 * An instance of this class is always associated with only
 * one <tt>Avatar</tt> and depends on that parent objects lifecycle,
 * it is a component.
 *
 * TODO Monsters and Chests might also have an inventory in a future version.
 *
 * Based on the Hibernate reference implementation "CaveatEmptor.JPA"
 *
 * @see Avatar
 */
@Entity
@DiscriminatorValue("CONT_INV")
public class Inventory extends Container {

    //TODO repeat code from equipment, possible refactor into another inheritance level
    @OneToOne
    @JoinColumn(name = "fighterID")
    private Avatar avatar;

    //Default no-args constructor
    protected Inventory() {}

    /**
     * Full constructor.
     *
     */
    protected Inventory(Avatar avatar, int maxSize) {
        super(maxSize);
        this.avatar = avatar;
    }

    // ********************** Model Methods ********************** //


    //use this method to manage the bidirectional pointers
    @Override
    public void addItem(Item item) {
        super.addItem(item);
        //Remove the bidirectional pointers between the equipment and item
        item.getContainedIn().removeItem(item);
        item.setContainedIn(this);
    }


    //Remove a given item from this inventory, not sure it's needed. O(n) worst case.
    @Override
    public void removeItem(Item item) {
        super.removeItem(item);
    }

    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Inventory)) {
            return false;
        }
        Inventory inventory = (Inventory) obj;
        return uuid != null && uuid.equals(inventory.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
