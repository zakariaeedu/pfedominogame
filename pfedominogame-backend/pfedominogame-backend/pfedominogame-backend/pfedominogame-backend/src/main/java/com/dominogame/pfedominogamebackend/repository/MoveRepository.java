
/*package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Game;
import com.dominogame.pfedominogamebackend.model.Move;
import com.dominogame.pfedominogamebackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {



    // Recherche un mouvement par son ID
    Optional<Move> findBymoveId(Long moveId);

    // Recherche tous les mouvements pour un jeu spécifique
    List<Move> findByGameId(Long gameId);

    // Recherche tous les mouvements pour un joueur spécifique
    List<Move> findByPlayerId(Long playerId);

    // Autres méthodes personnalisées selon vos besoins
    List<Move> findByPlayerAndGame(Player player, Game game);
}*/
package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Game;
import com.dominogame.pfedominogamebackend.model.Move;
import com.dominogame.pfedominogamebackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    Optional<Move> findById(Long id);
    List<Move> findByPlayer(Player player);
    List<Move> findByPlayerAndGame(Player player, Game game);
    List<Move> findByGame(Game game);
}



