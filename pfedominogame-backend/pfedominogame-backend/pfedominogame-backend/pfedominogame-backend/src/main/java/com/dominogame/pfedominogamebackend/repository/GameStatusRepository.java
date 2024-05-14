package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Game;
import com.dominogame.pfedominogamebackend.model.Gamestatus;
import com.dominogame.pfedominogamebackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameStatusRepository extends JpaRepository<Gamestatus, Long> {
    Optional<Gamestatus> findByGamestatusId(Long gamestatusId);
    List<Gamestatus> findByGame(Game game);
    List<Gamestatus> findByCurrentPlayer(Player player);



}
