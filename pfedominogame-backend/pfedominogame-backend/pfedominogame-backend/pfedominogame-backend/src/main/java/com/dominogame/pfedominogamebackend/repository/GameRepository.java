
package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.EnumGameStatus;
import com.dominogame.pfedominogamebackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


    Optional<Game> findByGameId(Long gameId);
    boolean deleteByGameId(Long gameId);

    List<Game> games = List.of();
    public static Optional<Game> findFirstByStatus(EnumGameStatus gameStatus) {
        for (Game game : games) {
            if (game.getStatus() == gameStatus) {
                return Optional.of(game);
            }
        }
        return Optional.empty(); // No game found with the specified status
    }
}


