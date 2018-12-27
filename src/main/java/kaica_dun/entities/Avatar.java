package kaica_dun.entities;

import kaica_dun.dao.AvatarHibernateDao;
import kaica_dun_system.User;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = true, updatable = false)
    private User user;

    //TODO Very uncertain about correct cascades here! Think more!
    //http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#entity-hibspec-cascade
    @OneToOne(mappedBy = "wielder", optional = true, cascade = CascadeType.ALL)
    private Item equippedWeapon;

    @OneToOne(mappedBy = "wearer", optional = true, cascade = CascadeType.ALL)
    private Item equippedArmor;

    @Transient
    private Random rand;    // todo: randomness only applies to monster generation?

    public Avatar() {
    }

    // Creating a new Avatar from user input with defaults.
    public Avatar(String name, String description, User user) {
        setDefaults();
        this.name = name;
        this.description = description;
        this.type = "User Avatar";
        this.user = user;
    }


    // Creating a new avatar - depreciated?
    public Avatar(User user, String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        this.user = user;
    }
    // Creating a new avatar - depreciated?
    public Avatar(User user, String name, String description, String type, int currHealth, int maxHealth, int damage, int armor, Item equippedWeapon) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        this.user = user;
        equippWeapon(equippedWeapon);
    }



    /**
     * Set the defaults for the Avatar from standard avatar.
     *
     * todo: fetch Avatar defaults from store.
     */
    private void setDefaults() {
        this.name = "No name";
        this.description = "No description";
        this.type = "No type";
        this.currHealth = 90;
        this.maxHealth = currHealth;
        this.damage = 1;
        this.armor = 2;
    }




    // ********************** Accessor Methods ********************** //
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    // ********************** Model Methods ********************** //
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



    // ********************** Common Methods ********************** //
    @Override
    public String toString() {
        // User user, String name, String description, String type, int currHealth, int maxHealth, int damage, int armor, Item equippedWeapon
        String string = String.format(
                "\nAvatar:" +
                        "\nUser: %s" +
                        "\nAvatar name: %s" +
                        "\ndescription: %s" +
                        "\nHealth %s" +
                        "\nMax Health: %s" +
                        "\nDamage: %s" +
                        "\nArmor: %s",
                user.getName(), this.getName(), this.getDescription(), this.getType(), this.getCurrHealth(), this.getMaxHealth(), this.getDamage(), this.getArmor());

        return string;
    }




}
