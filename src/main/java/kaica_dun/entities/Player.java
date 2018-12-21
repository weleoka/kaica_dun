package kaica_dun.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {

    // Field variable declarations and Hibernate annotation scheme
    @Id @GeneratedValue
    @Column(name = "playerID")
    private Long playerId;

    @Basic
    @Column(name = "playerName")
    private String playerName;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "totalDeaths")
    private int totalDeaths;

    @Basic
    @Column(name = "totalScore")
    private int totalScore;

    @Basic
    @Column(name = "highScore")
    private int highScore;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerID", nullable = false)
    private List<Dungeon> dungeons;


    // Default empty constructor
    public Player(){}



    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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
        return playerId == that.playerId &&
                Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, playerName);
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getTotalDeaths() {
        return totalDeaths;
    }


    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }


    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }


    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    public List<Dungeon> getDungeons() {
        return this.dungeons;
    }

    public void setDungeons(List<Dungeon> dungeons) {
        this.dungeons = dungeons;
    }
}
