package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;

import javax.persistence.*;
import java.util.Objects;

/**
 * Working on abstract superclass stuff for PlayerAvatar and Monster to implement combat logic.
 */
@Entity
@Table(name = "fighter")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fighter_discriminator")
public abstract class Fighter implements Describable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fighterID", updatable = false, nullable = false)
    protected Long fighterId;

    @Basic
    @Column(name = "fighter_name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "fighter_type")
    private String type;

    @Basic
    @Column(name = "curr_health")
    private int currHealth;

    @Basic
    @Column(name = "max_health")
    private int maxHealth;

    @Basic
    @Column(name = "damage")
    private int damage;

    @Basic
    @Column(name = "armor")
    private int armor;

    public Fighter() {}

    /**
     * The standard constructor for a fighter, which is the superclass of both Monster and Avatar.
     *
     * @param name          name of the monster/avatar
     * @param description   description of the monster/avatar
     * @param type          the type of the monster/avatar (monster ex: Orc, Skeleton. avatar ex: Mage, Barbarian)
     * @param currHealth    the current health of the monster/avatar
     * @param maxHealth     the possible maximum health of the monster/avatar
     * @param damage        the damage dealt by the monster/the damage dealt by the avatar when no weapon is equipped
     * @param armor         the armor of the monster/the armor of the avatar when no armor is equipped
     */
    public Fighter(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.currHealth = currHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.armor = armor;
    }

    // ********************** Accessor Methods ********************** //

    public Long getFighterId() {
        return fighterId;
    }

    public void setFighterId(Long fighterId) {
        this.fighterId = fighterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    // ********************** Model Methods ********************** //

    abstract void takeDamage(int damage);

    abstract int dealDamage();

    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fighter that = (Fighter) o;
        return fighterId.equals(that.fighterId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(type, that.type) &&
                currHealth == that.currHealth &&
                maxHealth == that.maxHealth &&
                damage == that.damage &&
                armor == that.armor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fighterId, name, description, type, currHealth, maxHealth, damage, armor);
    }
}
