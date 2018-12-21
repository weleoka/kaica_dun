package kaica_dun.entities_BACKUP;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "consumableitem")
public class ConsumableItem {
    private Long itemId;
    private int uses;

    protected ConsumableItem(){}

    @Id
    @Column(name = "itemID")
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "uses")
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
        ConsumableItem that = (ConsumableItem) o;
        return itemId == that.itemId &&
                uses == that.uses;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, uses);
    }
}
