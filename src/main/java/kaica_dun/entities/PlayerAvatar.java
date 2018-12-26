package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "playeravatar")
public class PlayerAvatar {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatarID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "avatar_name")
    private String avatarName;

    @Basic
    @Column(name = "curr_health")
    private int currHealth;

    @Basic
    @Column(name = "max_health")
    private int maxHealth;

    @Basic
    @Column(name = "base_armor")
    private int baseArmor;

    @Basic
    @Column(name = "base_damage")
    private int baseDamage;

    @Basic
    @Column(name = "description")
    private String description;



    // Default empty constructor
    protected PlayerAvatar(){}


    public Long getAvatarId() {
        return id;
    }

    public void setAvatarId(Long avatarId) {
        this.id = avatarId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
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

    public int getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(int baseArmor) {
        this.baseArmor = baseArmor;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

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
        return id.equals(that.id) &&
                currHealth == that.currHealth &&
                maxHealth == that.maxHealth &&
                baseArmor == that.baseArmor &&
                baseDamage == that.baseDamage &&
                Objects.equals(avatarName, that.avatarName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, avatarName, currHealth, maxHealth, baseArmor, baseDamage, description);
    }
}
