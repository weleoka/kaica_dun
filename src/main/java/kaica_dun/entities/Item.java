package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "itemID", updatable = false, nullable = false)
    private Long itemId;

    @Basic
    @Column(name = "itemName")
    private String itemName;


    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "playerID")
    private Long playerId;




    // Default empty constructor
    protected Item(){}



    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return itemId == that.itemId &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, description, playerId);
    }
}
