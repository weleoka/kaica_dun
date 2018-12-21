package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "monster")
public class Monster implements Describable, Lootable {

    // Field variable declarations and Hibernate annotation scheme
    @Id @GeneratedValue
    @Column(name = "monsterID")
    private Long monsterId;

    //TODO unsure of mapping strategy
    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Room room;

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



    public Monster(Room room, int armor, int currHealth, int damage, String description, int maxHealth, String name, String type) {
        this.room = room;
        this.armor = armor;
        this.currHealth = currHealth;
        this.damage = damage;
        this.description = description;
        this.maxHealth = maxHealth;
        this.name = name;
        this.type = type;
    }

    public Monster(int armor, int currHealth, int damage, String description, int maxHealth, String name, String type) {
        this.armor = armor;
        this.currHealth = currHealth;
        this.damage = damage;
        this.description = description;
        this.maxHealth = maxHealth;
        this.name = name;
        this.type = type;
    }

    public Long getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Long monsterId) {
        this.monsterId = monsterId;
    }



    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        return monsterId == that.monsterId &&
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
        return Objects.hash(monsterId, armor, currHealth, damage, description, maxHealth, name, type);
    }

    @Override
    public void lootItem() {
        //Needs to be rethought. Needs to change the state of the looted Object as well as the PlayerAvatar and then
        // update db.
    }
}
