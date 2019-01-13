package kaica_dun.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
     * Full constructor.
     */
    public Armor(String itemName, String description, int armorValue) {
        super(itemName, description);
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


}
