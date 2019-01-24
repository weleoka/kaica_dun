package kaica_dun.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ARM")
public class Armor extends Item {

    @Basic
    @Column(name = "armor_value")
    private int armorValue;

    @OneToOne(optional = true)
    @JoinColumn(name = "wearerID")
    private Avatar wearer;

    // Default empty constructor
    protected Armor() {}

    /**
     * create without container. //TODO temporary while equipment is being reworked.
     */
    public Armor(String itemName, String description, int armorValue) {
        super(itemName, description);
        this.armorValue = armorValue;
    }

    /**
     * Full constructor.
     */
    public Armor(String itemName, String description, int armorValue, Container container) {
        super(itemName, description, container);
        this.armorValue = armorValue;
    }


    // ********************** Accessor Methods ********************** //

    public int getArmor() {
        return armorValue;
    }

    public void setArmor(int armorValue) {
        this.armorValue = armorValue;
    }

    public Avatar getWearer() { return this.wearer; }

    public void setWearer(Avatar wearer) { this.wearer = wearer; }

    public int getArmorValue() { return armorValue; }

    public void setArmorValue(int armorValue) { this.armorValue = armorValue; }

    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Armor)) {
            return false;
        }
        Armor armor = (Armor) obj;
        return uuid != null && uuid.equals(armor.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
