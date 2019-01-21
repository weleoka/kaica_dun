package kaica_dun.entities;

import kaica_dun.resources.ItemFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.LinkedList;
import java.util.List;

@Entity
@DiscriminatorValue("CONT_LOOT")
public class LootContainer extends Container {

    protected LootContainer() {}

    LootContainer(int rewardLevel) {
        super(10);
    }

    LootContainer(boolean starterChest) {
        super(10);
        this.getItems().add(ItemFactory.createDragonSlayer(this));
    }

    /**
     * @param container    the {@code Inventory} which is to have the {@code Item} added to it
     * @return             an {@code Item}, its reference now removed from the {@code LootContainer}
     */
    public Item moveNextItem(Container container) {
        //isEmpty is O(n) for both best and worst case probably.
        if (this.getItems().isEmpty()) {
            return null;
        }
        Item tmp = this.getItems().iterator().next();
        container.addItem(tmp);
        this.getItems().remove(tmp);
        return tmp;
    }

    /**
     *
     * @param container    the {@code Inventory} which is to have the {@code Item}s added to it
     * @return             a {@code LinkedList} of the objects to be looted, or {@code null} if there are no {@code Item}s to loot
     */
    public List<Item> moveAll(Container container) {
        //isEmpty is O(n) for both best and worst case probably.
        if (this.getItems().isEmpty()) {
            return null;
        }
        //TODO possible refactor because of recreating a datastructure
        List<Item> loot = new LinkedList<>();
        loot.addAll(this.getItems());
        container.addAllItems(loot);
        this.removeAll();
        return loot;
    }

    /**
     * Static adding of item for testing
     */
    public void addTestItem() {
        this.getItems().add(ItemFactory.createDragonSlayer(this));
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof LootContainer)) {
            return false;
        }
        LootContainer lootContainer = (LootContainer) obj;
        return uuid != null && uuid.equals(lootContainer.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
