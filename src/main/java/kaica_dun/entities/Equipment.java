package kaica_dun.entities;

import kaica_dun.resources.ItemFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Maps the weapon to index 0, the Armor to index 1 and an Amulet(not yet implemented) to index 2.
 */
@Entity
@DiscriminatorValue("CONT_EQU")
public class Equipment extends Container {

    @OneToOne
    @JoinColumn(name = "fighterID")
    private Avatar avatar;

    protected Equipment() {}

    public Equipment(Avatar avatar) {
        super(3);
        this.avatar = avatar;
    }

    // ********************** Accessor Methods ********************** //

    public Item getItem(int index) {
        return getItems().get(index);
    }

    //TODO Not to fond of these casts, possible refactor
    public Weapon getWeapon() {
        return (Weapon) items.get(0);
    }

    public Armor getArmor() {
        return (Armor) items.get(1);
    }

    public Amulet getAmulet() {
        return (Amulet) getItems().get(2);
    }

    // ********************** Model Methods ********************** //

    /**
     * @param item         the {@code Item} which is to be equipped
     * @return             the {@code Item} that was equipped
     */
    public Item equippItem(Item item) {
        if(item instanceof Weapon) {
            if(this.getItem(0) != null) {
                this.unEquippItem(this.getItem(0));
            }
            this.getItems().set(0, item);
            item.setContainedIn(this);
        } else if(item instanceof Armor) {
            if(this.getItem(1) != null) {
                this.unEquippItem(this.getItem(1));
            }
            this.getItems().set(1, item);
            item.setContainedIn(this);
        } else if(item instanceof Amulet) {
            if(this.getItem(2) != null) {
                this.unEquippItem(this.getItem(2));
            }
            this.getItems().set(2, item);
            item.setContainedIn(this);
        }
        return item;
    }

    /**
     * @param item         the {@code Item} which is to be unequipped
     * @return             the {@code Item} that was unequipped
     */
    public Item unEquippItem(Item item) {
        //Updating bidirectional pointers
        getItems().remove(item);
        item.setContainedIn(avatar.getInventory());
        return item;
    }

    /**
     * Static adding of item for testing
     */
    public void addTestItem() {
        this.getItems().add(ItemFactory.createDragonSlayer(this));
    }
}

