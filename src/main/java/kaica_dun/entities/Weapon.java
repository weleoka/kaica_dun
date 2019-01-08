package kaica_dun.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("WEP")
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
    Weapon() {}

    public Weapon(String itemName, String description, int lowDamage, int damageRange) {
        super(itemName, description);
        this.lowDamage = lowDamage;
        this.damageRange = damageRange;
    }


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
