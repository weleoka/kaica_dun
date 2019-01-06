package kaica_dun.entities;

import kaica_dun.interfaces.Describable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MO")
public class Monster extends Fighter implements Describable {

    @ManyToOne
    @JoinColumn(name = "roomID", nullable = true, updatable = false)
    private Room room;

    // Default empty constructor
    protected Monster(){}

    public Monster(String name, String description, String type, int currHealth, int maxHealth, int damage, int armor) {
        super(name, description, type, currHealth, maxHealth, damage, armor);
    }


    // ********************** Accessor Methods ********************** //

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String getDescription() {
        if(this.getCurrHealth() <= 0) {
            return super.getDescription() + " It's dead.";
        } else { return super.getDescription(); }
    }


    // ********************** Model Methods ********************** //


    //Current health is set to: current health - (the taken damaged reduced by armor)
    @Override
    public int takeDamage(int damage) {
        int takenDamage = damage - getArmor();
        setCurrHealth(getCurrHealth() - (takenDamage));

        return takenDamage;
    }

    @Override
    public int dealDamage() { return getDamage(); }

    @Override
    public int hit(Fighter opponent){
        int dealtDamage = opponent.takeDamage(this.dealDamage());

        return dealtDamage;
    }

    @Override
    public String toString() {
        String str = String.format("Name: %s, type: %s, HP: %s, MaxHP: %s, damage: %s, armor: %s",
                getName(), getType(), getCurrHealth(), getMaxHealth(), getDamage(), getArmor());
        return str;
    }
}
