
package com.dominogame.pfedominogamebackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private Long gameId;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Domino> lineOfPlay = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "player1_id")
    @JsonIgnore
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    @JsonIgnore
    private Player player2;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Gamestatus> GameSituations;

    @Enumerated(EnumType.STRING)
    private EnumGameStatus status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "winner_id")

    private Player winner;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Move> moves = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "current_player_id")
    @JsonIgnore
    private Player currentPlayer;

    public void attachDominoToChain(Domino domino, char side) {
        if (lineOfPlay.isEmpty()) {
            lineOfPlay.add(domino);
            return;
        }

        if (side == 'g') {
            // Attach to the left of the chain
            Domino firstDomino = lineOfPlay.get(0);
            if (domino.getRightValue() == firstDomino.getLeftValue()) {
                lineOfPlay.add(0, domino);
            } else if (domino.getLeftValue() == firstDomino.getLeftValue()) {
                domino.reverse();
                lineOfPlay.add(0, domino);
            }
        } else if (side == 'd') {
            // Attach to the right of the chain
            Domino lastDomino = lineOfPlay.get(lineOfPlay.size() - 1);
            if (domino.getLeftValue() == lastDomino.getRightValue()) {
                lineOfPlay.add(domino);
            } else if (domino.getRightValue() == lastDomino.getRightValue()) {
                domino.reverse();
                lineOfPlay.add(domino);
            }
        }
    }

    public void addToLineOfPlay(Domino domino) {
        lineOfPlay.add(domino);
    }
    @PrePersist
    public void initializeCurrentPlayer() {
        // Logique pour définir le joueur actuel automatiquement
        if (player1 != null) {
            currentPlayer = player1;
        }

    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<Domino> getLineOfPlay() {
        return lineOfPlay;
    }

    public void setLineOfPlay(List<Domino> lineOfPlay) {
        this.lineOfPlay = lineOfPlay;
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

    public List<Gamestatus> getGameSituations() {
        return GameSituations;
    }

    public void setGameSituations(List<Gamestatus> gameSituations) {
        GameSituations = gameSituations;
    }

    public EnumGameStatus getStatus() {
        return status;
    }

    public void setStatus(EnumGameStatus status) {
        this.status = status;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}

















