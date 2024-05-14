

package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByscoreId(Long scoreId);
    Optional<Score> findById(Long id);


}


