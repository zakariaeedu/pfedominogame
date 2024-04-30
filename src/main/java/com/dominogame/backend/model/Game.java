package com.dominogame.backend.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Map;


@Entity
@Table(name="games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gameId")
    private String gameId;
    @OneToOne
    private Player player1;
    @OneToOne
    private Player player2;
    @Column(name = "gamestatus")
    private GameStatus status;
    @Column(name = "winner")
    private Player winner;



    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }


}
