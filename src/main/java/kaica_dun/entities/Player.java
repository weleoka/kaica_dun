package main.java.kaica_dun.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "player", schema = "kaicadungeon")
public class Player {
    private int playerId;
    private String playerName;
    private String password;
    private int totalDeaths;
    private int totalScore;
    private int highScore;

    protected Player(){}

    @Id
    @Column(name = "playerID")
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Basic
    @Column(name = "playerName")
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

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "totalDeaths")
    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    @Basic
    @Column(name = "totalScore")
    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Basic
    @Column(name = "highScore")
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
