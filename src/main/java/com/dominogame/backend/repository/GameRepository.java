package com.dominogame.backend.repository;

import com.dominogame.backend.model.Game;
import com.dominogame.backend.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
    Optional<Game> findFirstByStatus(GameStatus gameStatus);
}