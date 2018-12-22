package kaica_dun.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {

    // Field variable declarations and Hibernate annotation scheme
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerID", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "playerName")
    private String playerName;

    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerID", nullable = false)
    private List<Dungeon> dungeons;


    // Default empty constructor
    public Player(){}

    public Player(String playerName, String password, List<Dungeon> dungeons) {

    }

    public Long getPlayerId() {
        return id;
    }

    public void setPlayerId(Long playerId) {
        this.id = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player that = (Player) o;
        return id.equals(that.id) &&
                Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerName);
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

    public void setDungeons(List<Dungeon> dungeons) {
        this.dungeons = dungeons;
    }
}
