package kaica_dun.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
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
    @GeneratedValue
    @Column(name = "containerID", updatable = false, nullable = false)
    protected Long id;

    @NaturalId
    @Type(type="uuid-char")             //Will not match UUIDs i MySQL otherwise
    @Column(nullable = false, unique = true)
    protected UUID uuid = UUID.randomUUID();

    @Basic
    @Column(name = "max_size")
    private int maxSize;

    @OneToMany(mappedBy = "containedIn", cascade = CascadeType.ALL)
    private Set<Item> items;

    //Default no-args constructor
    protected Container() {}

    /**
     * Full constructor.
     *
     */
    protected Container(int maxSize) {
        this.maxSize = maxSize;
        if(this.items == null) {
            this.items = new LinkedHashSet<>(maxSize);
        }
    }

    // ********************** Accessor Methods ********************** //

    public int getMaxSize() { return maxSize; }

    public void setMaxSize(int maxSize) { this.maxSize = maxSize; }

    public Set<Item> getItems() { return items; }

    public void setItems(Set<Item> items) { this.items = items; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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
        return uuid != null && uuid.equals(container.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
