package kaica_dun.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "container")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "container_discriminator")
@DiscriminatorValue("CONT")
public class Container {

    @Id
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "containerID")
    private UUID id;

    @Basic
    @Column(name = "max_size")
    private int maxSize;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items;

    //Default no-args constructor
    protected Container() {}

    /**
     * Full constructor.
     *
     */
    protected Container(int maxSize) {
        this.maxSize = 20;
        this.items = new LinkedHashSet<>(maxSize);
    }

    // ********************** Accessor Methods ********************** //

    public int getMaxSize() { return maxSize; }

    public void setMaxSize(int maxSize) { this.maxSize = maxSize; }

    public Set<Item> getItems() { return items; }

    public void setItems(Set<Item> items) { this.items = items; }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    // ********************** Model Methods ********************** //

    //Remove a given item from this inventory, not sure it's needed. O(n) worst case.
    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeAll() { items.clear(); }

    public void addAllItems(List<Item> items) {
        getItems().addAll(items);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Container)) {
            return false;
        }
        Container container = (Container) obj;
        return id != null && id.equals(container.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
