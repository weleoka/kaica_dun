package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Container;
import kaica_dun.entities.Item;
import kaica_dun.interfaces.Lootable;

import java.util.List;

public interface ItemService {

    /**
     * Take one item inside a lootable object. Moves the item from one
     * {@code Container} instance to another and persists changes
     *
     * @param avatar
     * @param lootable
     */
    void lootOne(Avatar avatar, Lootable lootable);

    /**
     * Take all items inside a lootable object. Moves the items from one
     * {@code Container} instance to another and persists changes
     *
     * @param avatar
     * @param lootable
     */
    void lootAll(Avatar avatar, Lootable lootable);

    /**
     * Equipp an item in the appropriate slot.
     *
     * @param avatar    the avatar equipping the item.
     * @param index     the index of the item in the avatar's inventory
     * @return          the item that was equipped
     */
    Item equipp(Avatar avatar, int index);

    /**
     * Return a list of inventory items.
     *
     * @param avatar    the avatar whose inventory is to be returned
     * @return          the list of items in the inventory
     */
    List<Item> getInventory(Avatar avatar);

    /**
     * Return a list of the currently equipped items on an avatar.
     *
     * @param avatar    the avatar whose equipment is to be returned
     * @return          the list of equipped items
     */
    List<Item> getEquipment(Avatar avatar);

    /**
     * TODO might not want this here at all, not thought this though properly.
     * Create new loot inside a container, which can be held in a chest TODO or monster, decorative and so on
     *
     * @param container     the {@code Container} to hold the new {@code Item}
     * @param rewardLevel   the level of reward to be created
     *                      TODO it's really dumb to expose this to the client, so they can set their own reward levels
     *                      TODO this is a PH so I don't have to involve the dungeon level, since we don't have one yet.
     * @return              the created {@code Item}
     */
    void createLoot(Container container, int rewardLevel);
}
