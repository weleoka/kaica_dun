package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

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
    public Armor(){}


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