package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item", schema = "kaicadungeon")
public class Item {
    private int itemId;
    private String itemName;
    private String description;
    private Integer playerId;

    protected Item(){}

    @Id
    @Column(name = "itemID")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "itemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "playerID")
    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
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
