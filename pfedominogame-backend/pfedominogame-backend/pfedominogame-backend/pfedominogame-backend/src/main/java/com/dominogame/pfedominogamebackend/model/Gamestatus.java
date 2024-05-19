package com.dominogame.pfedominogamebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Gamestatus {
    @Id
    private Long gamestatusId;
@ManyToOne
@JoinColumn(name = "gameId")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player currentPlayer;
    }
