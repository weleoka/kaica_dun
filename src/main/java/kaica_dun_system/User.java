package kaica_dun_system;

import kaica_dun.entities.Avatar;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
    @NaturalId
    @Column(name = "username")
    private String userName;

    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // See developer notes #04
    private Set<Avatar> avatars = new LinkedHashSet<Avatar>();

    //Unidirectional, I think
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Avatar currAvatar;


    // Default empty constructor
    protected User() {}


    /**
     * Full constructor.
     *
     * @param userName      name of the user
     * @param password      password of the user
     * @param currAvatar    the currently active avatar on the user account
     */
    public User(String userName, String password, Avatar currAvatar) {
        this.userName = userName;
        this.password = password;
        this.currAvatar = currAvatar;
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

    public Set<Avatar> getAvatars() { return this.avatars; }

    public Avatar getCurrAvatar() { return currAvatar; }

    public void setCurrAvatar(Avatar currAvatar) { this.currAvatar = currAvatar; }



    // ********************** Model Methods ********************** //

    public void addAvatar(Avatar avatar) {
        if (avatar == null) {
            throw new IllegalArgumentException("Can't add a null Avatar.");
        }
        avatars.add(avatar);
        //Set<Avatar> aaa = this.getAvatars();
        //aaa.add(avatar);
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
