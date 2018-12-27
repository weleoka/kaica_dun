package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import kaica_dun.interfaces.Lootable;

import javax.persistence.*;
import java.util.Objects;

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

    //Current health is set to: current health - (the taken damaged reduced by armor)
    @Override
    public void takeDamage(int damage) {
        setCurrHealth(getCurrHealth() - (damage - getArmor()));
    }

    @Override
    public int dealDamage() { return getDamage(); }
}
