package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Domino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DominoRepository extends JpaRepository<Domino, Long> {
    Optional<Domino> findByDominoId(Long dominoId);
    List<Domino> findByLeftValue(int leftValue);
    List<Domino> findByRightValue(int rightValue);
    void deleteByDominoId(Long dominoId);
    @Query("SELECT d FROM Domino d WHERE d.leftValue = :left AND d.rightValue = :right")
    List<Domino> findByLeftAndRight(@Param("left") int left, @Param("right") int right);
}

