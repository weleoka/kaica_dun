package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
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
    @Transactional
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
    @Transactional
    public void lootAll(Avatar avatar, Lootable lootable) {
        Inventory avatarInventory = avatar.getInventory();
        //Pointer setting
        List<Item> items = lootable.lootAll(avatarInventory);
        //Persisting pointer changes
        for(Item item : items) {
            itemInterface.save(item);
        }
        //TODO this might not be needed when Item is the owner of the relationship
        containerInterface.save(lootable.getContainer());
    }

}
