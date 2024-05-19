
package com.dominogame.pfedominogamebackend.repository;

import com.dominogame.pfedominogamebackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByplayerId(Long playerId);
    Optional<Player> findByUsername(String username);
    Optional<Player> findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Player p WHERE p.username = :username")
    boolean existsByUsername(@Param("username") String username);
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Player p WHERE p.email = :email")
    boolean existsByEmail(@Param("email") String email);

}

