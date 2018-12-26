package kaica_dun.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "itemName")
    private String itemName;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "low_damage")
    private int lowDamage;

    @Basic
    @Column(name = "high_damage")
    private int highDamage;


    @OneToOne(optional = true)
    @JoinColumn(name = "fighterID")
    private Avatar wielder;

    // Default empty constructor
    public Item(){}

    public Item(String itemName, String description, int lowDamage, int highDamage) {
        this.itemName = itemName;
        this.description = description;
        this.lowDamage = lowDamage;
        this.highDamage = highDamage;
    }

    public Long getItemId() {
        return id;
    }

    public void setItemId(Long itemId) {
        this.id = itemId;
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

    public Avatar getWielder() {
        return this.wielder;
    }

    public void setWielder(Avatar wielder) {
        this.wielder = wielder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return id.equals(that.id) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(description, that.description) &&
                lowDamage == that.lowDamage &&
                highDamage == that.highDamage &&
                Objects.equals(wielder, that.wielder);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, description, lowDamage, highDamage, wielder);
    }
}
