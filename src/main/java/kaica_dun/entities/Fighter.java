package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Working on abstract superclass stuff for PlayerAvatar and Monster to implement combat logic.
 */
@Entity
@Table(name = "fighter")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fighter_discriminator")
public abstract class Fighter {

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

    public Fighter(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.currHealth = currHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.armor = armor;
    }


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
