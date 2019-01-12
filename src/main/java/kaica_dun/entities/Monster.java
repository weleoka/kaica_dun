package kaica_dun.entities;

import kaica_dun.interfaces.Describable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MO")
public class Monster extends Fighter implements Describable {

    private static final Logger log = LogManager.getLogger();

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

    /**
     * Convenience method for checking if the monster is dead.
     * @return
     */
    public boolean deathCheck() {
        boolean isDead = false;
        if (getCurrHealth() <= 0) {
            isDead = true;
        }
        return isDead;
    }

    /**
     * Method to set monster health to 0.
     *
     * @return
     */
    public void setToDead() {
        log.debug("I AM SET TO DEAD: {}", id);
        this.currHealth = 0;
    }

    /**
     * Check if the monster is alive.
     *
     * @return
     */
    public boolean isAlive() {
        if (currHealth >= 1) {
            return true;
        }
        return false;
    }



    // ********************** Common Methods ********************** //


    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Monster)) {
            return false;
        }
        Monster monster = (Monster) obj;
        return id != null && id.equals(monster.id);
    }

    @Override
    public int hashCode() { return 13; }

    @Override
    public String toString() {
        String str = String.format("Name: %s, type: %s, HP: %s, MaxHP: %s, damage: %s, armor: %s",
                getName(), getType(), getCurrHealth(), getMaxHealth(), getDamage(), getArmor());
        return str;
    }
}
