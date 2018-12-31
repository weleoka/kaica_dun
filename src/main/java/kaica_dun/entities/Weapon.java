package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue("ARM")
public class Weapon extends Item {

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "low_damage")
    private int lowDamage;

    //TODO move out to Weapon once Item inheritance is up and running!
    @Basic
    @Column(name = "damage_range")
    private int damageRange;

    @OneToOne(optional = true)
    @JoinColumn(name = "wielderID")
    private Avatar wielder;

    // Default empty constructor
    public Weapon(){}


    // ********************** Accessor Methods ********************** //

    public int getLowDamage() { return lowDamage; }

    public void setLowDamage(int lowDamage) { this.lowDamage = lowDamage; }

    public int getDamageRange() { return damageRange; }

    public void setDamageRange(int damageRange) { this.damageRange = damageRange; }

    public Avatar getWielder() {
        return this.wielder;
    }

    public void setWielder(Avatar wielder) {
        this.wielder = wielder;
    }


}
