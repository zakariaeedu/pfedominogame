package com.dominogame.pfedominogamebackend.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId")
    private Long playerId;
    @OneToMany(mappedBy = "player")
    private List<Score> scores;
    private String username;
    private String email;
    @OneToMany(mappedBy = "player1")
    private List<Game> gamesAsPlayer1;
    @OneToMany(mappedBy = "player2")
    private List<Game> gamesAsPlayer2;
    public Iterable<Object> getSetofPlayer() {
        return null;
    }
    private int score;
    @Getter
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Domino> playerHand = new ArrayList<>();
    public void addDominoToHand(Domino domino) {
        playerHand.add(domino);
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Game> getGamesAsPlayer1() {
        return gamesAsPlayer1;
    }

    public void setGamesAsPlayer1(List<Game> gamesAsPlayer1) {
        this.gamesAsPlayer1 = gamesAsPlayer1;
    }

    public List<Game> getGamesAsPlayer2() {
        return gamesAsPlayer2;
    }

    public void setGamesAsPlayer2(List<Game> gamesAsPlayer2) {
        this.gamesAsPlayer2 = gamesAsPlayer2;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Domino> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Domino> playerHand) {
        this.playerHand = playerHand;
    }
}


