package kaica_dun.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

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

    //Default no-args constructor
    protected Inventory() {}

    /**
     * Full constructor.
     *
     */
    protected Inventory(int maxSize) {
        super(maxSize);
    }

    // ********************** Model Methods ********************** //

    //use this method to manage the bidirectional pointers
    @Override
    public void addItem(Item item) {
        super.addItem(item);
        //Remove the bidirectional pointers between the item and Avatar if item is Armor/Weapon
        if (item.getClass() == Weapon.class) {
            if (((Weapon)item).getWielder() != null){
                ((Weapon)item).getWielder().unEquippWeapon();
            }
        } else if (item.getClass() == Armor.class) {
            if (((Armor)item).getWearer() != null) {
                ((Armor)item).getWearer().unEquippArmor();}
        }
    }

    //Remove a given item from this inventory, not sure it's needed. O(n) worst case.
    @Override
    public void removeItem(Item item) {
        super.removeItem(item);
    }
}
