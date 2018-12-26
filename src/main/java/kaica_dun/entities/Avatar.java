package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

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


    // Default empty constructor
    public Avatar(){}

    public Avatar(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
    }

    public Avatar(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor, Item equippedWeapon) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        equippWeapon(equippedWeapon);
    }

    //Equipp a weapon in your EquippedWeapon slot
    public void equippWeapon(Item weapon){
        this.equippedWeapon = weapon;
        equippedWeapon.setWielder(this);
    }

    //Kill relationship on both entities
    public void unEquippWeapon(){
        equippedWeapon.setWielder(null);
        this.equippedWeapon = null;
    }



}
