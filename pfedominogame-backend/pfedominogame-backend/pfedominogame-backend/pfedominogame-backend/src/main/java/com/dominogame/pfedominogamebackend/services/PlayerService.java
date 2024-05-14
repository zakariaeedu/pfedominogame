package com.dominogame.pfedominogamebackend.services;

import com.dominogame.pfedominogamebackend.model.Player;
import com.dominogame.pfedominogamebackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;


    @Autowired
    public PlayerService(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
    }

    public Player getPlayerById(Long playerId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        return playerOptional.orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));
    }
    @Transactional
    public Player registerNewPlayer(String username, String email) {
        if (playerRepository.existsByUsername(username) || playerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        Player newPlayer = new Player();
        newPlayer.setUsername(username);
        newPlayer.setEmail(email);
        return playerRepository.save(newPlayer);
    }
    public Player registerPlayer(Player player) {
        // Vérifier si le joueur existe déjà dans la base de données
        if (player.getUsername() != null && playerRepository.existsByUsername(player.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà utilisé.");
        }

        if (player.getEmail() != null && playerRepository.existsByEmail(player.getEmail())) {
            throw new IllegalArgumentException("Cette adresse e-mail est déjà associée à un compte.");
        }
        return playerRepository.save(player);
    }
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    public Player updatePlayer(Long playerId, Player updatedPlayer) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setUsername(updatedPlayer.getUsername());
            player.setEmail(updatedPlayer.getEmail());
            return playerRepository.save(player);
        }
        return null;
    }
    public boolean deletePlayer(Long playerId) {

        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
            return true;
        }
        return false;
    }

}

