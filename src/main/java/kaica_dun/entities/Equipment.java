package kaica_dun.entities;

import kaica_dun.resources.ItemFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CONT_EQU")
public class Equipment extends Container {

    protected Equipment() {}

    public Equipment(boolean a) {
        super(3);

    }

    //TODO EVERYTHING! CHANGING TO MAP TO SUPPORT INDEXING!
    /**
     * @param item         the {@code Item} which is to be equipped
     * @return             the {@code Item} that was equipped
     */
    public Item equippItem(Item item) {
        //TODO PH
        return item;
    }

    /**
     * @param item         the {@code Item} which is to be unequipped
     * @return             the {@code Item} that was unequipped
     */
    public Item unEquippItem(Item item) {
        //TODO PH
        return item;
    }

    /**
     * Static adding of item for testing
     */
    public void addTestItem() {
        this.getItems().add(ItemFactory.createDragonSlayer(this));
    }
}

