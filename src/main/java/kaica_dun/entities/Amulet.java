package kaica_dun.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AMU")
public class Amulet extends Item {

    // Default empty constructor
    protected Amulet() {}

    /**
     * create without container. //TODO temporary while equipment is being reworked.
     */
    public Amulet(String itemName, String description) {
        super(itemName, description);
    }

    /**
     * Full constructor.
     */
    public Amulet(String itemName, String description, Container container) {
        super(itemName, description, container);
    }


    // ********************** Accessor Methods ********************** //


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Amulet)) {
            return false;
        }
        Amulet amulet = (Amulet) obj;
        return uuid != null && uuid.equals(amulet.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
