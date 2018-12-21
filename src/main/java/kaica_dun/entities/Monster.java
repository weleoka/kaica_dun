package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "monster")
public class Monster implements Describable, Lootable {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monsterID", updatable = false, nullable = false)
    private Long id;

    //TODO This should be a table join but currently is simply a non-important column.
    /*@OneToOne(optional = false)
    @PrimaryKeyJoinColumn*/
    @Basic
    @Column(name = "roomID")
    private Long roomID;

    @Basic
    @Column(name = "armor")
    private int armor;

    @Basic
    @Column(name = "curr_health")
    private int currHealth;

    @Basic
    @Column(name = "damage")
    private int damage;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "max_health")
    private int maxHealth;

    @Basic
    @Column(name = "monster_name")
    private String name;

    @Basic
    @Column(name = "monster_type")
    private String type;



    // Default empty constructor
    protected Monster(){}



    public Monster(Long roomID, int armor, int maxHealth, int currHealth, int damage, String description, String name, String type) {
        this.roomID = roomID;
        this.armor = armor;
        this.maxHealth = maxHealth;
        this.currHealth = currHealth;
        this.damage = damage;
        this.description = description;
        this.name = name;
        this.type = type;
    }

    public Monster(int armor, int maxHealth, int currHealth, int damage, String description, String name, String type) {
        this.armor = armor;
        this.currHealth = currHealth;
        this.damage = damage;
        this.description = description;
        this.maxHealth = maxHealth;
        this.name = name;
        this.type = type;
    }

    public Long getMonsterId() {
        return id;
    }

    public void setMonsterId(Long monsterId) {
        this.id = monsterId;
    }



    public Long getRoom() {
        return roomID;
    }

    public void setRoom(Long newRoomID) {
        this.roomID = newRoomID;
    }


    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }


    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String getDescription() {
        if(this.getCurrHealth() <= 0) {
            return description + " It's dead.";
        } else { return description; }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster that = (Monster) o;
        return id.equals(that.id) &&
                armor == that.armor &&
                currHealth == that.currHealth &&
                damage == that.damage &&
                maxHealth == that.maxHealth &&
                Objects.equals(description, that.description) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, armor, currHealth, damage, description, maxHealth, name, type);
    }

    @Override
    public void lootItem() {
        //Needs to be rethought. Needs to change the state of the looted Object as well as the PlayerAvatar and then
        // update db.
    }
}
