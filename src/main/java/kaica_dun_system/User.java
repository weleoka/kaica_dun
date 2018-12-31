package kaica_dun_system;

import kaica_dun.entities.Dungeon;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User")

/*
Example of using JPQL named query
JPA also provides a way for building static queries, as named queries, using the @NamedQuery and @NamedQueries annotations.
It is considered to be a good practice in JPA to prefer named queries over dynamic queries when possible.
*/
@NamedQuery(name="User.findByName", query="SELECT u FROM User u WHERE u.userName = :name")
public class User {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "username")
    private String userName;

    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Dungeon> dungeons = new LinkedList<Dungeon>();


    // Default empty constructor
    public User(){}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long userId) {
        this.id = userId;
    }

    public String getName() {
        return this.userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User that = (User) o;

        return id.equals(that.id) && Objects.equals(userName, that.userName) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Dungeon> getDungeons() {
        return this.dungeons;
    }

    public void addDungeon(Dungeon dungeon) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Can't add a null Dungeon.");
        }
        this.getDungeons().add(dungeon);
        dungeon.setUser(this);
    }




}
