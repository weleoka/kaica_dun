package kaica_dun.entities;

import kaica_dun.interfaces.Describable;

import javax.persistence.*;
import java.util.Objects;

/**
 * TODO abstract superclass stuff for PlayerAvatar and Monster to implement combat logic.
 */
@Entity
@Table(name = "Fighter")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fighter_discriminator")
public abstract class Fighter implements Describable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fighterID", updatable = false, nullable = false)
    protected Long id;

    @Basic
    @Column(name = "fighter_name")
    public String name;

    @Basic
    @Column(name = "description")
    public String description;

    @Basic
    @Column(name = "fighter_type")
    public String type;

    @Basic
    @Column(name = "curr_health")
    public int currHealth;

    @Basic
    @Column(name = "max_health")
    public int maxHealth;

    @Basic
    @Column(name = "damage")
    public int damage;

    @Basic
    @Column(name = "armor")
    public int armor;

    public Fighter() {}


    /**
     * The standard constructor for a fighter, which is the superclass of both Monster and Avatar.
     *
     * Possibly depreciated as defaults are set by subclasses on creation, or loaded from persistence.
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

    public Long getId() {
        return id;
    }

    public void setId(Long fighterId) {
        this.id = fighterId;
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

    abstract void hit(Fighter opponent);


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fighter that = (Fighter) o;
        return id.equals(that.id) &&
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
        return Objects.hash(id, name, description, type, currHealth, maxHealth, damage, armor);
    }


}
