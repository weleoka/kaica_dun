package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Item_consumable")
public class ItemConsumable {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_consumableID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "uses")
    private int uses;


    // Default empty constructor
    private ItemConsumable(){}

    public Long getItemId() {
        return id;
    }

    public void setItemId(Long itemId) {
        this.id = itemId;
    }


    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemConsumable that = (ItemConsumable) o;
        return id.equals(that.id) &&
                uses == that.uses;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uses);
    }
}
