package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

/**
 * Testing inheritance strategies to make PlayerAvatar and Monster use inheritance. Both extend Character which houses
 * most fields and methods.
 */
@Entity
@DiscriminatorValue("PA")
public class Avatar extends Fighter {


    //TODO Very uncertain about correct cascades here! Think more!
    @OneToOne(mappedBy = "wielder", optional = true, cascade = CascadeType.ALL)
    private Item equippedWeapon;

    @OneToOne(mappedBy = "wearer", optional = true, cascade = CascadeType.ALL)
    private Item equippedArmor;

    @Transient
    private Random rand;

    // Default empty constructor
    public Avatar(){}

    public Avatar(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
    }

    public Avatar(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor, Item equippedWeapon) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        equippWeapon(equippedWeapon);
    }

    //TODO change after Item inheritance is done
    //Equipp a weapon in your EquippedWeapon slot
    public void equippWeapon(Item weapon){
        this.equippedWeapon = weapon;
        equippedWeapon.setWielder(this);
    }

    //TODO change after Item inheritance is done
    //Unequipp your currently equipped weapon. Set references to null on both entities.
    public void unEquippWeapon(){
        equippedWeapon.setWielder(null);
        this.equippedWeapon = null;
    }

    //TODO change after Item inheritance is done
    //Equipp a weapon in your EquippedWeapon slot
    public void equippArmor(Item armor){
        this.equippedArmor = armor;
        equippedArmor.setWearer(this);
    }

    //TODO change after Item inheritance is done
    //Unequipp your currently equipped weapon. Set references to null on both entities.
    public void unEquippArmor(){
        equippedArmor.setWearer(null);
        this.equippedArmor = null;
    }

    //TODO replace once item inheritance is up and running!
    public Item getEquippedArmor() { return equippedArmor; }

    public void setEquippedArmor(Item equippedArmor) { this.equippedArmor = equippedArmor; }

    @Override
    public void takeDamage(int damage) {
        if (equippedArmor == null) {
            setCurrHealth(getCurrHealth() - (damage - getArmor()));
        } else {
            setCurrHealth(getCurrHealth() - (damage - equippedArmor.getArmorValue())); ;
        }
    }

    @Override
    public int dealDamage() {
        int damage = 0;
        if (equippedWeapon == null) {
            damage = getDamage();
        } else {
            //nextInt is non-inclusive, so needs +1 to match damageRange
            damage = equippedWeapon.getLowDamage() + rand.nextInt(equippedWeapon.getDamageRange() + 1);
        }
        return damage;
    }





}
