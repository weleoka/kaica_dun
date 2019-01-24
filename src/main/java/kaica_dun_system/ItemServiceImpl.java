package kaica_dun_system;

import kaica_dun.dao.ContainerInterface;
import kaica_dun.dao.ItemInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Inventory;
import kaica_dun.entities.Item;
import kaica_dun.interfaces.Lootable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that deals with moving items around in the dungeon, such as moving, looting and destroying items.
 */
@Service
@EnableTransactionManagement
public class ItemServiceImpl {

    // Fields declared
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ItemInterface itemInterface;

    @Autowired
    private ContainerInterface containerInterface;

    // ********************** Creation methods ********************** //



    // ********************** Item-moving methods ********************** //

    /**
     * Take one item from a lootable object such as a chest.
     *
     * @param avatar        the avatar that is looting the lootable
     * @param lootable      the lootable object to take the {@code Item} from
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void lootOne(Avatar avatar, Lootable lootable) {
        Inventory avatarInventory = avatar.getInventory();
        //Pointer setting
        Item item = lootable.lootNextItem(avatarInventory);
        //Persisting pointer changes
        itemInterface.save(item);
        //TODO this might not be needed when Item is the owner of the relationship
        containerInterface.save(lootable.getContainer());
    }

    /**
     * Take one item from a lootable object such as a chest.
     *
     * @param avatar        the avatar that is looting the lootable
     * @param lootable      the lootable object to take the {@code Item} from
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void lootAll(Avatar avatar, Lootable lootable) {
        Inventory avatarInventory = avatar.getInventory();
        //Pointer setting
        List<Item> items = lootable.lootAll(avatarInventory);
        //Persisting pointer changes
        for(Item item : items) {
            if(item != null) {
                log.debug("The following item was looted {}.", item.getName());
                itemInterface.save(item);
            }
        }
        //TODO this might not be needed when Item is the owner of the relationship
        containerInterface.save(lootable.getContainer());
        System.out.println("Items in avatar inventory after looting: ");
        for(Item i : avatar.getInventory().getItems()) {
            if(i != null) {
                System.out.println();
            }
        }
    }

    /**
     * Take an item from the inventory and equip it in the appropriate slot.
     *
     * @param avatar    the avatar which holds the item in its inventory
     * @param index     the index of the item in the inventory.items-list
     *
     * @return          the item that was equipped
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Item equipp(Avatar avatar, int index) {
        Inventory avatarInventory = avatar.getInventory();
        //Pointer setting
        Item item = avatarInventory.getItems().get(index);

        //Persisting pointer changes
        log.debug("Equipped {}.", item.getName());
        itemInterface.save(item);
        containerInterface.save(avatar.getInventory());
        containerInterface.save(avatar.getEquipment());

        return item;
    }

    /**
     * Retuns a list of the items in the avatar's inventory.
     *
     * @param avatar    the avatar who's inventory items are to be returned
     * @return          a list of the items in the inventory
     */
    public List<Item> getInventory(Avatar avatar) {
        return avatar.getInventory().getItems();
    }

    /**
     * Retuns a list of the items equipped on the avatar.
     *
     * @param avatar    the avatar who's equipment items are to be returned
     * @return          a list of the items equipped on the avatar
     */
    public List<Item> getEquipment(Avatar avatar) {
        return avatar.getEquipment().getItems();
    }
}
