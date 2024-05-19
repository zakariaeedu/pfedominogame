package com.dominogame.pfedominogamebackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Domino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dominoId;

    // Autres champs et méthodes de l'entité Domino

    @ManyToOne
    @JoinColumn(name = "game_id")

    @JsonIgnore
    private Game game;
    private int leftValue;
    private int rightValue;
    @ManyToOne

    @JoinColumn(name = "winner_id")

    private Player winner;

    @ManyToOne
    @JoinColumn(name = "player_id")

    @JsonIgnore
    private Player player;

    @Override
    public String toString() {
        return "[" + leftValue + "|" + rightValue + "]";
    }

    public void reverse() {
       int temp = leftValue;
       leftValue = rightValue;
       rightValue = temp;
   }
    public Long getDominoId() {
        return dominoId;
    }

    public void setDominoId(Long dominoId) {
        this.dominoId = dominoId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(int leftValue) {
        this.leftValue = leftValue;
    }

    public int getRightValue() {
        return rightValue;
    }

    public void setRightValue(int rightValue) {
        this.rightValue = rightValue;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public int getWeight(){return leftValue+rightValue;}
    public Domino(int l, int r){
        leftValue = l;
        rightValue = r;
    }
}