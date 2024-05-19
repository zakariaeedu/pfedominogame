
package com.dominogame.pfedominogamebackend.model;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long moveId;


    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "dominoId")
    private Domino domino;
    private int moveNumber;
    private char side;

    public Long getMoveId() {
        return moveId;
    }

    public void setMoveId(Long moveId) {
        this.moveId = moveId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Domino getDomino() {
        return domino;
    }

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }
}


