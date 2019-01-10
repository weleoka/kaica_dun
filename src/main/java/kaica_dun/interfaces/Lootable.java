package kaica_dun.interfaces;

import kaica_dun.entities.Item;

import java.util.List;

/**
 * Inteface for objects that can be looted for items and gold.
 *
 * Needs to be rethought for database implementation.
 */
public interface Lootable {
    public abstract Item lootItem(int index);
    public abstract List<Item> lootAll();
}
