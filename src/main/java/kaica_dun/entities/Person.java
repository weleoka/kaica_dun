package main.java.kaica_dun.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This class is created by Kai Weeks for testing purposes of
 * persistance and logging systems.
 *
 * See file TestDb for details on what is going on and needs to be done.
 *
 */
@Entity
@Table(name="person")
public class Person {

    protected Person(){}

    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
