package kaica_dun.interfaces;

import kaica_dun.entities.Container;
import kaica_dun.entities.Inventory;
import kaica_dun.entities.Item;

import java.util.List;

/**
 * Inteface for objects that can be looted for items and gold.
 *
 * Needs to be rethought for database implementation.
 */
public interface Lootable {
    //TODO I really want this to be lootItem(int index), but right now Hibernate is making that hard. Maybe later.

    /**
     * Loot the next Item contained in the Lootable object.
     *
     * @return  an Item null if there are no items contained in the Lootable object
     */
    public abstract Item lootNextItem(Inventory inventory);

    /**
     * Loot all Items contained in the Lootable object.
     *
     * @return  returns null if there are no items contained in the Lootable object, otherwise returns a List of Items
     */
    public abstract List<Item> lootAll(Inventory inventory);

    public abstract Container getContainer();
}
