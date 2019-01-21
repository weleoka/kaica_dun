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
    protected Weapon() {}

    //TODO remove when equipment is reworked to be a container
    public Weapon(String itemName, String description, int lowDamage, int damageRange) {
        super(itemName, description);
        this.lowDamage = lowDamage;
        this.damageRange = damageRange;
    }

    /**
     * Full constructor.
     * @param itemName      name of the weapon
     * @param description   the weapon's description
     * @param lowDamage     lowest possible raw damage the weapon can deal
     * @param damageRange   the range of additional damage the weapon can deal on top of the {@code lowDamage}
     * @param container     the container the weapon is to be held in on creation
     */
    public Weapon(String itemName, String description, int lowDamage, int damageRange, Container container) {
        super(itemName, description, container);
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


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Weapon)) {
            return false;
        }
        Weapon weapon = (Weapon) obj;
        return uuid != null && uuid.equals(weapon.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
