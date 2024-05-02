package com.dominogame.backend.controller;

import com.dominogame.backend.GameService.GameService;
import com.dominogame.backend.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class GameController {

    private final GameService gameService;
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index"; //index.html
    }

    @PostMapping("/joinGame")
    public String joinGame(@RequestBody Player player) {
        // Call the GameService method to connect the player to a game
        Game game = gameService.connectToGame(player);
        // Redirect to the game ply
        return "redirect:/gamePlay";
    }
    @PostMapping("/gamePlay")
    public void gamePlay(@RequestBody Game game) {
//        simpMessagingTemplate.convertAndSend("/topic/game-progress"+game.getGameId(),game);
        gameService.initiateGame(game);
    }
    @PostMapping("/gamePlay")
    public void makemove(@RequestBody Game game,int index,char side,char skipMove) {
        simpMessagingTemplate.convertAndSend("/topic/game-progress"+game.getGameId(),game);
        gameService.makeMove(game,index,side,skipMove);
    }


    @PostMapping("/exitGame")
    public String exitGame(HttpSession session) {
        // Retrieve the current game instance from the session
        Game currentGame = (Game) session.getAttribute("currentGame");

        // Retrieve the current player from the session
        Player currentPlayer = (Player) session.getAttribute("currentPlayer");

        // Call the GameService method to handle leaving the game
        gameService.leaveGame(currentGame.toString(), currentPlayer);

        // Redirect to the homepage or any other appropriate page
        return "redirect:/";
    }



}

