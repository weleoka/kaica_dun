package kaica_dun.entities;

import kaica_dun_system.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

/**
 * Testing inheritance strategies to make PlayerAvatar and Monster use inheritance. Both extend Character which houses
 * most fields and methods.
 */
@Entity
@DiscriminatorValue("PA")
@NamedQuery(name="Avatar.findByUserID", query="SELECT a FROM Avatar a WHERE a.user = :userInstance")//query="SELECT u FROM User u WHERE u.userName = :name")
public class Avatar extends Fighter {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", nullable = true, updatable = false)
    private User user;

    //Unidirectional, the Dungeon doesn't "know" there's an avatar in it. TODO think! TEST!
    @OneToOne
    @Fetch(FetchMode.JOIN)
    private Dungeon currDungeon;

    //Unidirectional, the Room doesn't "know" there's an avatar in it. TODO think! TEST!
    @OneToOne(cascade = CascadeType.ALL)
    private Room currRoom;

    //Seems correct to cascade almost everything here, so ALL is a good PH-strategy. TODO remove some of the cascades.
    //http://docs.jboss.org/hibernate/stable/annotations/reference/en/html_single/#entity-hibspec-cascade
    @OneToOne(mappedBy = "wielder", optional = true, cascade = CascadeType.ALL)
    private Weapon equippedWeapon;

    @OneToOne(mappedBy = "wearer", optional = true, cascade = CascadeType.ALL)
    private Armor equippedArmor;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @PrimaryKeyJoinColumn
    private Inventory inventory;

    @Transient
    private Random rand = new Random();

    protected Avatar() {}

    // Creating a new Avatar from user input with defaults.
    public Avatar(String name, String description, User user) {
        setDefaults();
        this.name = name;
        this.description = description;
        this.type = "User Avatar";
        this.user = user;               //For use in the dealDamage-method.
        // todo: not implemented because creating the inventory cant be done before knowing the AvatarId(FighterId)
        this.inventory = new Inventory(this);
    }


    /**
     * Full constructor. Needs a Weapon and Armor.
     *
     * @param user
     * @param name
     * @param description
     * @param type
     * @param currHealth
     * @param damage
     * @param armor
     * @param equippedWeapon
     * @param equippedArmor
     */
    public Avatar(String name, String description, User user, String type, int currHealth, int damage,
                  int armor, Weapon equippedWeapon, Armor equippedArmor) {
        setDefaults();
        this.name = name;
        this.description = description;
        this.type = type;
        this.currHealth = currHealth;
        //this.maxHealth = maxHealth; // Currently disabled.
        this.damage = damage;
        this.armor = armor;
        this.user = user;
        equippWeapon(equippedWeapon);
        equippArmor(equippedArmor);
        this.inventory = new Inventory(this);
    }


    /**
     * Barebones constructor. Creates an avatar without a weapon or an armor.
     *
     * @param user
     * @param name
     * @param description
     * @param type
     * @param currHealth
     * //@param maxHealth // disabled right now.
     * @param damage
     * @param armor
     */
    public Avatar(User user, String name, String description, String type, int currHealth, int damage, int armor) {
        setDefaults();
        this.name = name;
        this.description = description;
        this.type = type;
        this.currHealth = currHealth;
        //this.maxHealth = maxHealth; // Currently disabled.
        this.damage = damage;
        this.armor = armor;
        this.user = user;
        this.inventory = new Inventory(this);
    }


    /**
     * Set the defaults for the Avatar from standard avatar.
     *
     * todo: fetch Avatar defaults from data store.
     */
    private void setDefaults() {
        this.name = "No name";
        this.description = "No description";
        this.type = "No type";
        this.currHealth = 300;
        this.maxHealth = currHealth;
        this.damage = 12;
        this.armor = 2;
        this.inventory = new Inventory(this);
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
        if(this.inventory != null) {
            if (this.getInventory().getItems().contains(weapon)) {
                this.getInventory().removeItem(weapon);
            }
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
        if (this.inventory != null) {
            if (this.getInventory().getItems().contains(armor)) {
                this.getInventory().removeItem(armor);
            }
        }
    }

    //Unequipp your currently equipped weapon. Set references to null on both entities.
    public void unEquippArmor(){
        equippedArmor.setWearer(null);
        this.equippedArmor = null;
    }


    @Override
    public int takeDamage(int damage) {
        int takenDamage;

        if (equippedArmor == null) {
            takenDamage = damage - getArmor();
            setCurrHealth(getCurrHealth() - (takenDamage));
        } else {
            takenDamage = damage - equippedArmor.getArmorValue();
            setCurrHealth(getCurrHealth() - (takenDamage)); ;
        }

        return takenDamage;
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
    public int hit(Fighter opponent){

        int dealtDamage = opponent.takeDamage(this.dealDamage());

        return dealtDamage;
    }

    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Avatar)) {
            return false;
        }
        Avatar avatar = (Avatar) obj;
        return id != null && id.equals(avatar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
                user.getName(), this.getName(), this.getDescription(), this.getCurrHealth(), this.getMaxHealth(), this.getDamage(), this.getArmor());

        return string;
    }
}
