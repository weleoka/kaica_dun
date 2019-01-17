package kaica_dun.entities;

import kaica_dun.interfaces.Lootable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Chest")
public class Chest implements Lootable {

    @Id
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "chestID", updatable = false, nullable = false)
    protected UUID id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn
    private Inventory inventory;

    protected Chest() {}

    // ********************** Accessor Methods ********************** //


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // ********************** Model Methods ********************** //
    @Override
    public List<Item> lootAll() { return null; //TODO ph
        }

    @Override
    public Item lootItem(int index) { return null; //TODO ph
        }


    // ********************** Common Methods ********************** //

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Chest)) {
            return false;
        }
        Chest chest = (Chest) obj;
        return id != null && id.equals(chest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
