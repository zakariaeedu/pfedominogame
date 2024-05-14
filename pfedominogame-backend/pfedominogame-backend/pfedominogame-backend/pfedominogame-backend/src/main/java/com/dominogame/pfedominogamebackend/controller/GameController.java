
package com.dominogame.pfedominogamebackend.controller;
import com.dominogame.pfedominogamebackend.model.Game;
import com.dominogame.pfedominogamebackend.model.Player;
import com.dominogame.pfedominogamebackend.repository.GameRepository;
import com.dominogame.pfedominogamebackend.repository.MoveRepository;
import com.dominogame.pfedominogamebackend.services.GameService;
import com.dominogame.pfedominogamebackend.repository.PlayerRepository;
import com.dominogame.pfedominogamebackend.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private  PlayerRepository playerRepository;
    private  GameRepository gameRepository;
    private final GameService gameService;
    private final PlayerService playerService;
    private final MoveRepository moveRepository;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService1, MoveRepository moveRepository) {
        this.gameService = gameService;
        this.playerService = playerService1;
        this.moveRepository = moveRepository;
    }
    @GetMapping({"/all","/"})
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> Games =gameService.getAllGames();
        return ResponseEntity.ok(Games);
    }


    @PostMapping("/{gameId}/join")
    public ResponseEntity<Game> joinGame(@PathVariable Long gameId, @RequestParam Long playerId) {
        Game joinedGame = gameService.joinGame(gameId, playerId);
        return ResponseEntity.ok(joinedGame);
    }

   @PostMapping("/{gameId}/starting")
   public ResponseEntity<Object> initialGame(@PathVariable Long gameId) {
       try {
           Game startedGame = gameService.initialGame(gameId);
           return ResponseEntity.ok(startedGame);
       } catch (IllegalArgumentException e) {
           return ResponseEntity.notFound().build();
       } catch (IllegalStateException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot start game without two players");
       }

   }
    @PostMapping("/{gameId}/gamePlay")
    public ResponseEntity<Game> gamePlay(
            @PathVariable Long gameId,
            @RequestParam Long playerId,
            @RequestBody int dominoIndex,
            @RequestBody char side,
            @RequestBody char skip
    ) {
        Game game = gameService.gamePlay(gameId, playerId, dominoIndex, side,skip);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        simpMessagingTemplate.convertAndSend("/topic/game-progress"+game.getGameId(),game);

        return ResponseEntity.ok(game);
    }



  @PostMapping("/create")
    public ResponseEntity<Game> connectToGame(@RequestParam Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Game createdGame = gameService.connectToGame(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable Long gameId) {
        Game game = gameService.getGameById(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    //EXIT THE GAME BY PLAYER

    @PostMapping("/exitGame")
    public String exitGame(@PathVariable Long gameId,Player currentPlayer) {
        // Retrieve the current game instance from the session

        // Call the GameService method to handle leaving the game
        gameService.leaveGame(gameId, currentPlayer);

        // Redirect to the homepage or any other appropriate page
        return "redirect:/";
    }

}







