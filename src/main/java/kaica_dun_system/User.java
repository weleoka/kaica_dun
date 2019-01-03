package kaica_dun_system;

/*
Example of using JPQL named query
JPA also provides a way for building static queries, as named queries, using the @NamedQuery and @NamedQueries annotations.
It is considered to be a good practice in JPA to prefer named queries over dynamic queries when possible.
*/

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User")
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Dungeon> dungeon = new LinkedList<Dungeon>();


    //TODO dafuq? Usure of mappings, currently mapping onto same column for both Dungeon and Avatar, works?
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Dungeon currDungeon;
    @OneToOne(optional = true, cascade = CascadeType.ALL)  //TODO cascades?
    private Avatar currAvatar;


    // Default empty constructor
    public User() {}



    /**
     * Full constructor.
     *
     * @param userName      name of the user
     * @param password      password of the user
     * @param currAvatar    the currently active avatar on the user account
     * @param currDungeon   the currently active dungeon on the user account
     */
    public User(String userName, String password, Avatar currAvatar, Dungeon currDungeon) {
        this.userName = userName;
        this.password = password;
        this.currAvatar = currAvatar;
        this.currDungeon = currDungeon;
    }

    /**
     * Partial constructor.
     *
     * @param userName      name of the user
     * @param password      password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // ********************** Accessor Methods ********************** //

    public Long getId() { return id; }

    private void setId(Long userId) { this.id = userId; }

    public String getName() { return this.userName; }

    public void setName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public List<Dungeon> getDungeon() { return this.dungeon; }

    public Avatar getCurrAvatar() { return currAvatar; }

    public void setCurrAvatar(Avatar currAvatar) { this.currAvatar = currAvatar; }

    public Dungeon getCurrDungeon() { return currDungeon; }

    public void setCurrDungeon(Dungeon currDungeon) { this.currDungeon = currDungeon; }


    // ********************** Model Methods ********************** //

    public void addDungeon(Dungeon dungeon) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Can't add a null Dungeon.");
        }
        this.getDungeon().add(dungeon);
        dungeon.setUser(this);
    }


    // ********************** Common Methods ********************** //

    @Override
    public String toString() {
        return "User: " + userName + "\n" + "Id: " + id;

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

}
