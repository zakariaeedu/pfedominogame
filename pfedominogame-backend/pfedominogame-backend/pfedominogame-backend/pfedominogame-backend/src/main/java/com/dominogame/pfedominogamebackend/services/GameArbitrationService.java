package com.dominogame.pfedominogamebackend.services;

import com.dominogame.pfedominogamebackend.model.Game;
import com.dominogame.pfedominogamebackend.model.EnumGameStatus;
import com.dominogame.pfedominogamebackend.model.Player;
import com.dominogame.pfedominogamebackend.model.Score;
import com.dominogame.pfedominogamebackend.repository.GameRepository;
import com.dominogame.pfedominogamebackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameArbitrationService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    @Autowired
    public GameArbitrationService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }
    @Transactional
    public void arbitrateGame(Long gameId) {
        // Find the game by its ID
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));
        if (game.getPlayer1() == null || game.getPlayer2() == null) {
            throw new IllegalStateException("Game cannot be arbitrated without two players");
        }
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        game.setStatus(EnumGameStatus.FINISHED);
        Player winner = determineWinner(player1, player2);
        game.setWinner(winner);
        gameRepository.save(game);
    }
    public Player determineWinner(Player player1, Player player2) {
        int scorePlayer1 = calculateScore(player1);
        int scorePlayer2 = calculateScore(player2);

        if (scorePlayer1 > scorePlayer2) {
            return player1;
        } else if (scorePlayer2 > scorePlayer1) {
            return player2;
        } else {
            return null;
        }
    }
    private int calculateScore(Player player) {
        int totalScore = 0;
        for (Score score : player.getScores()) {
            totalScore += score.getGameScore();
        }
        return totalScore;
    }

}

