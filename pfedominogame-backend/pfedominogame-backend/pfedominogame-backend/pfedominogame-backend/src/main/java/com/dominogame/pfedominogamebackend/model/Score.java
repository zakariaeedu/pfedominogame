

package com.dominogame.pfedominogamebackend.model;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;
    private int gameScore;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;
    private int score;  // Champ pour le score

}


