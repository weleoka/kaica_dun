package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "playeravatar", schema = "kaicadungeon")
public class PlayerAvatar {
    private int avatarId;
    private String avatarName;
    private int currHealth;
    private int maxHealth;
    private int baseArmor;
    private int baseDamage;
    private String description;

    @Id
    @Column(name = "avatar_id")
    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    @Basic
    @Column(name = "avatar_name")
    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    @Basic
    @Column(name = "curr_health")
    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    @Basic
    @Column(name = "max_health")
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Basic
    @Column(name = "base_armor")
    public int getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(int baseArmor) {
        this.baseArmor = baseArmor;
    }

    @Basic
    @Column(name = "base_damage")
    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAvatar that = (PlayerAvatar) o;
        return avatarId == that.avatarId &&
                currHealth == that.currHealth &&
                maxHealth == that.maxHealth &&
                baseArmor == that.baseArmor &&
                baseDamage == that.baseDamage &&
                Objects.equals(avatarName, that.avatarName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatarId, avatarName, currHealth, maxHealth, baseArmor, baseDamage, description);
    }
}
