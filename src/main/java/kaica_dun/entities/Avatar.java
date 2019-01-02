package kaica_dun.entities;

import kaica_dun_system.User;

import javax.persistence.*;
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


//    @OneToOne(mappedBy = "currAvatar")
//    private User currUser;

    //Unidirectional, the Dungeon doesn't "know" there's an avatar in it. TODO think! TEST!
    @OneToOne
    private Dungeon currDungeon;

    //Unidirectional, the Room doesn't "know" there's an avatar in it. TODO think! TEST!
    @OneToOne
    private Room currRoom;

    //Seems correct to cascade almost everything here, so ALL is a good PH-strategy. TODO remove some of the cascades.
    //http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#entity-hibspec-cascade
    @OneToOne(mappedBy = "wielder", optional = true, cascade = CascadeType.ALL)
    private Weapon equippedWeapon;

    @OneToOne(mappedBy = "wearer", optional = true, cascade = CascadeType.ALL)
    private Armor equippedArmor;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Inventory inventory = new Inventory();

    @Transient
    private Random rand;

    public Avatar() {
    }

    // Creating a new Avatar from user input with defaults.
    public Avatar(String name, String description, User user) {
        setDefaults();
        this.name = name;
        this.description = description;
        this.type = "User Avatar";
        this.user = user;               //For use in the dealDamage-method.
    }


    // Creating a new avatar - depreciated?
    public Avatar(User user, String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        this.user = user;
    }

    /**
     * Full constructor. Needs a Weapon and Armor.
     *
     * @param user
     * @param name
     * @param description
     * @param type
     * @param currHealth
     * @param maxHealth
     * @param damage
     * @param armor
     * @param equippedWeapon
     * @param equippedArmor
     * @param inventory
     */
    public Avatar(User user, String name, String description, String type, int currHealth, int maxHealth, int damage,
                  int armor, Weapon equippedWeapon, Armor equippedArmor, Inventory inventory) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        this.user = user;
        equippWeapon(equippedWeapon);
        equippArmor(equippedArmor);
        this.inventory = inventory;
    }

    /**
     * Barebones constructor. Creates an avatar without a weapon or an armor.
     *
     * @param user
     * @param name
     * @param description
     * @param type
     * @param currHealth
     * @param maxHealth
     * @param damage
     * @param armor
     * @param inventory
     */
    public Avatar(User user, String name, String description, String type, int currHealth, int maxHealth, int damage,
                  int armor, Inventory inventory) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
        this.user = user;
        this.inventory = inventory;
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

    public Armor getEquippedArmor() { return equippedArmor; }

    public void setEquippedArmor(Armor equippedArmor) { this.equippedArmor = equippedArmor; }

    public Weapon getEquippedWeapon() { return equippedWeapon; }

    public void setEquippedWeapon(Weapon equippedWeapon) { this.equippedWeapon = equippedWeapon; }

    public Inventory getInventory() { return inventory; }

//    public User getCurrUser() { return currUser; }

 //   public void setCurrUser(User currUser) { this.currUser = currUser; }

    public Dungeon getCurrDungeon() { return currDungeon; }

    public void setCurrDungeon(Dungeon currDungeon) { this.currDungeon = currDungeon; }

    public Room getCurrRoom() { return currRoom; }

    public void setCurrRoom(Room currRoom) { this.currRoom = currRoom; }


    // ********************** Model Methods ********************** //

    //Equipp a weapon in your EquippedWeapon slot
    public void equippWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
        equippedWeapon.setWielder(this);
        //Remove the weapon from Avatar.inventory if it's in the inventory
        if (this.getInventory().getItems().contains(weapon)) {
            this.getInventory().getItems().remove(weapon);
        }
    }

    //Unequipp your currently equipped weapon. Set references to null on both entities.
    public void unEquippWeapon(){
        equippedWeapon.setWielder(null);
        this.equippedWeapon = null;
    }

    //Equipp a weapon in your EquippedWeapon slot
    public void equippArmor(Armor armor){
        this.equippedArmor = armor;
        equippedArmor.setWearer(this);
        //Remove the armor from Avatar.inventory if it's in the inventory
        if (this.getInventory().getItems().contains(armor)) {
            this.getInventory().getItems().remove(armor);
        }
    }

    //Unequipp your currently equipped weapon. Set references to null on both entities.
    public void unEquippArmor(){
        equippedArmor.setWearer(null);
        this.equippedArmor = null;
    }


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

    @Override
    public void hit(Fighter opponent){
        opponent.takeDamage(this.dealDamage());
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
