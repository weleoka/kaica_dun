package kaica_dun.entities2;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "monster2")
public class Monster2 {

    // Field variable declarations and Hibernate annotation scheme
    @Id @GeneratedValue
    @Column(name = "monsterID")
    private Long monsterId;

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
    protected Monster2(){}

    public Monster2(int armor, int maxHealth, int currHealth, int damage, String type, String description, String name) {
        this.armor = armor;
        this.maxHealth = maxHealth;
        this.currHealth = currHealth;
        this.damage = damage;
        this.type = type;
        this.description = description;
        this.name = name;
    }

    public Long getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Long monsterId) {
        this.monsterId = monsterId;
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
        Monster2 that = (Monster2) o;
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


}
