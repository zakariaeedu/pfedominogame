package com.dominogame.pfedominogamebackend.services;

import com.dominogame.pfedominogamebackend.model.*;
import com.dominogame.pfedominogamebackend.repository.DominoRepository;
import com.dominogame.pfedominogamebackend.repository.GameRepository;
import com.dominogame.pfedominogamebackend.repository.MoveRepository;
import com.dominogame.pfedominogamebackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Long.valueOf;


@Service
public class GameService {
    private  final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameArbitrationService gameArbitrationService;
    private final DominoRepository dominoRepository;

    private final MoveRepository moveRepository;
    private final PlayerService playerService;


    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, GameArbitrationService gameArbitrationService, DominoRepository dominoRepository, MoveRepository moveRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gameArbitrationService = gameArbitrationService;
        this.dominoRepository = dominoRepository;
        this.moveRepository = moveRepository;
        this.playerService = playerService;
    }
    // variables
    private ArrayList<Domino> unplayedDominoes = new ArrayList<>();
    private ArrayList<Domino> setofPlayer1 = new ArrayList<>();
    private ArrayList<Domino> setofPlayer2 = new ArrayList<>();
    private ArrayList<Domino> lineofPlay = new ArrayList<>();
    private ArrayList<Domino> setofPlayer = null;

    @Transactional
    public Game connectToGame(Player player) {
        // Find a game with status NEW
        Optional<Game> optionalGame = GameRepository.findFirstByStatus(EnumGameStatus.NEW);

        // If no game with status NEW is found, create a new game
        Game game = optionalGame.orElseGet(() -> {
            Game newGame = new Game();
            newGame.setPlayer1(player);
            newGame.setGameId(UUID.randomUUID().getMostSignificantBits());
            newGame.setStatus(EnumGameStatus.NEW);
            return newGame;
        });

        //newGame.setGameId(UUID.randomUUID().getMostSignificantBits());

        // Update the game with player2 and change status to IN_PROGRESS

        if (game.getPlayer1() != null && game.getPlayer2() == null) {
            // Set the second player
            game.setPlayer2(player);
            game.setStatus(EnumGameStatus.IN_PROGRESS);
        }

        // Save the game entity to the database
        gameRepository.save(game);
        return game;
    }


    //join to the game with the game id

    @Transactional
    public Game joinGame(Long gameId, Long playerId) {
        // Récupérer le jeu à partir de l'ID
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));

        // Récupérer le joueur à partir de l'ID du joueur
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + playerId));

        // Vérifier si le jeu a déjà deux joueurs
        if (game.getPlayer1() != null && game.getPlayer2() != null) {
            throw new IllegalStateException("Game already has two players");
        }

        // Si player1 est vide, l'assigner à player1
        if (game.getPlayer1() == null) {
            game.setPlayer1(player);
        } else {
            // Si player1 est déjà attribué, assigner à player2
            game.setPlayer2(player);
        }

        // Enregistrer et retourner le jeu mis à jour
        return gameRepository.save(game);
    }
   @Transactional
   public Game initialGame(Long gameId) {
       // Récupérer le jeu à partir de l'ID
       Game game = gameRepository.findById(gameId)
               .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));

       // Vérifier si le jeu est prêt à démarrer (par exemple, s'il a déjà deux joueurs)
       if (game.getPlayer1() == null || game.getPlayer2() == null) {
           throw new IllegalStateException("Cannot start game without two players");
       }

       // Vérifier si les joueurs existent
       Player player1 = playerRepository.findById(game.getPlayer1().getPlayerId())
               .orElseThrow(() -> new IllegalArgumentException("Player1 not found with ID: " + game.getPlayer1().getPlayerId()));

       Player player2 = playerRepository.findById(game.getPlayer2().getPlayerId())
               .orElseThrow(() -> new IllegalArgumentException("Player2 not found with ID: " + game.getPlayer2().getPlayerId()));

       // Attribution des joueurs au jeu
       game.setPlayer1(player1);
       game.setPlayer2(player2);

       Domino x = null, y = null, z = null;
       do {
           initiateUnplayedDominoes();
           initiateSetofPlayer(setofPlayer1);
           initiateSetofPlayer(setofPlayer2);
           x = getGreatestDouble(setofPlayer1);
           y = getGreatestDouble(setofPlayer2);
           if (x.getWeight() > y.getWeight()) {
               setofPlayer = setofPlayer1;
               z = x;
               break;
           }
           if (x.getWeight() < y.getWeight()) {
               setofPlayer = setofPlayer2;
               z = y;
               break;
           }
       } while (x.getWeight() == y.getWeight());
       moveDominotoLineofPlay(setofPlayer, setofPlayer.indexOf(z), 'd');

       gameRepository.save(game);
       return game;

   }
    @Transactional
    public Game gamePlay(Long gameId, Long playerId, int dominoIndex, char side,char skip) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));
        // check if the player is the current player

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer == null || !currentPlayer.getPlayerId().equals(playerId)) {
            throw new IllegalArgumentException("Player with ID " + playerId + " is not the current player in the game");
        }
        List<Domino> playerHand = currentPlayer.getPlayerHand();
        if (dominoIndex < 0 || dominoIndex >= playerHand.size()) {
            throw new IllegalArgumentException("Invalid domino index: " + dominoIndex);
        }
        Domino dominoToPlay = playerHand.get(dominoIndex);

        String msg = "";

        if (!canPlay(setofPlayer)) {
            if (!unplayedDominoes.isEmpty())
                moveDominoRandomly(unplayedDominoes, setofPlayer);
        }

        if (skip == 'y') {
            // Player chose to skip their turn
            msg = "Player skipped their turn.";
        } else {
            // Handle player's move
            if ((dominoIndex < setofPlayer.size()) && (side == 'g' || side == 'd') && isValidMove(setofPlayer.get(dominoIndex), side)) {
                moveDominotoLineofPlay(setofPlayer, dominoIndex, side);
            } else {
                msg = "Invalid move. Player's choice is not valid.";
            }
        }

        if (setofPlayer.isEmpty() || isGameFinished()) {
            String[] res = getScore(getWeight(setofPlayer1), getWeight(setofPlayer2));
            // Display game result
        }

        // Save the game state
        gameRepository.save(game);

        // save the move
        Move move = new Move();
        move.setGame(game);
        move.setPlayer(currentPlayer);
        move.setDomino(dominoToPlay);
        move.setSide(side);
        moveRepository.save(move);
        // when the game and the move saved will switch the current player
        switchPlayer(game);
        return game;
    }


    public void initiateUnplayedDominoes() {
        unplayedDominoes.clear();
        for (int i = 0; i <= 6; i++)
            for (int j = 0; j <= i; j++)
                unplayedDominoes.add(new Domino(i, j));
    }

    public void initiateSetofPlayer(ArrayList<Domino> setofPlayer) {
        setofPlayer.clear();
        for (int i = 0; i <= 6; i++)
            moveDominoRandomly(unplayedDominoes, setofPlayer);
    }

    public Domino getGreatestDouble(ArrayList<Domino> setofPlayer) {
        Domino r = new Domino(-1, -1);
        for (Domino d : setofPlayer)
            if ((d.getLeftValue() == d.getRightValue()) && (d.getWeight() > r.getWeight()))
                r = d;
        return r;
    }

    public void moveDominoRandomly(ArrayList<Domino> listSource, ArrayList<Domino> listDestination){
        Random rand = new Random();
        int r = rand.nextInt(listSource.size());
        moveDomino(listSource, listDestination, r);
    }
    //Méthode moveDomino
    public void moveDomino(ArrayList<Domino> listSource, ArrayList<Domino> listDestination, int index){
        listDestination.add(listSource.get(index));
        listSource.remove(index);
    }
    public void moveDominotoLineofPlay(ArrayList<Domino> listSource, int index, char sideofLine){
        Domino d = listSource.get(index);
        listSource.remove(index);
        if(lineofPlay.isEmpty()){
            lineofPlay.add(d);
            return;
        }
        if(sideofLine == 'g'){
            if(lineofPlay.get(0).getLeftValue() != d.getRightValue())
                d.reverse();
            lineofPlay.add(0, d);
        }
        else{
            if(lineofPlay.get(lineofPlay.size()-1).getRightValue() != d.getLeftValue())
                d.reverse();
            lineofPlay.add(d);
        }
    }

    //Méthode isGameFinished
    private boolean isGameFinished() {
        return (!canPlay(setofPlayer1)) && (!canPlay(setofPlayer2)) && (unplayedDominoes.isEmpty());
    }

    //Méthode isValidMove
    private boolean isValidMove(Domino d, char sideofPlayLine) {
        if(lineofPlay.isEmpty())
            return true;
        if(sideofPlayLine == 'g')
            return((lineofPlay.get(0).getLeftValue() == d.getRightValue()) || (lineofPlay.get(0).getLeftValue() == d.getLeftValue()));
        else
            return((lineofPlay.get(lineofPlay.size()-1).getRightValue() == d.getRightValue()) || (lineofPlay.get(lineofPlay.size()-1).getRightValue() == d.getLeftValue()));
    }

    //Méthode canPlay
    public boolean canPlay(ArrayList<Domino> setofPlayer){
        for(Domino d:setofPlayer)
            if(isValidMove(d, 'g') || isValidMove(d, 'd'))
                return true;
        return false;
    }

    //Méthode getWeight
    public int getWeight(ArrayList<Domino> setofPlayer){
        if(setofPlayer.isEmpty())
            return -1;
        int w = 0;
        for(Domino d:setofPlayer)
            w += d.getWeight();
        return w;
    }

    //Méthode getScore
    public String [] getScore(int n1, int n2){
        String [] res = new String[2];
        if(n1 < n2){
            res[0] = "Felicitation! Vous avez gagne!";
            res[1] = "Desole! Vous avez perdu.";
        }
        if(n1 > n2){
            res[0] = "Desole! Vous avez perdu.";
            res[1] = "Felicitation! Vous avez gagne!";
        }
        if(n1 == n2)
            res[0] = res[1] = "Le jeu se termine par une egalite!";
        return res;
    }

    //Méthode toString(ArrayList<Domino> setofPlayer)
    public String toString(ArrayList<Domino> setofPlayer){
        String str; //"Your set of dominos #";
        if((lineofPlay.size() < 1) || ((lineofPlay.size() == 1) && (setofPlayer.size() >= 7)))
            str = "Vos dominos : ";
        else
            str = "Vos dominos restants : ";
        if(setofPlayer.size() == 1)
            str = "Votre domino restant : ";
        int i = 1;
        for(Domino d:setofPlayer)
            str += (i++) + "-" + d.toString() + "  ";
        return str + "#";
    }

    //Méthode toString()
    public String toString(){
        String str = "La chaine de jeu : "; //"Line of play #";
        for(Domino d:lineofPlay)
            str += d.toString();
        return str + "#";
    }
    @Transactional
    public void leaveGame(Long gameId, Player currentPlayer) {
        // Find the game by its gameId
        Optional<Game> optionalGame = gameRepository.findById((long) Math.toIntExact(valueOf(gameId)));
        // If the game exists, remove the player from the game
        optionalGame.ifPresent(game -> {

            if (currentPlayer.equals(game.getPlayer1())) {
                game.setPlayer1(null);
            } else if (currentPlayer.equals(game.getPlayer2())) {
                game.setPlayer2(null);
            }
            Player winnerPlayer =(game.getPlayer1() != null) ? game.getPlayer1() : game.getPlayer2();
            game.setWinner(winnerPlayer);
            // Update the game status or perform any other necessary cleanup
            game.setStatus(EnumGameStatus.FINISHED); // Set to some appropriate status
            // Save the updated game entity
            gameRepository.save(game);
        });
    }
    public List<Game> getAllGames() {

        return gameRepository.findAll();
    }
    public Game getGameById(Long gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        return gameOptional.orElseThrow(() -> new RuntimeException("Game not found with ID: " + gameId));


    }

    // switch players
    private void switchPlayer(Game game) {
        if (game.getCurrentPlayer().equals(game.getPlayer1())) {
            game.setCurrentPlayer(game.getPlayer2());
        } else {
            game.setCurrentPlayer(game.getPlayer1());
        }
    }
    // status
    public String getGameStatus(Game game, Player currentPlayer) {
        StringBuilder status = new StringBuilder();
        status.append("Line of play: ").append(getLineOfPlay(game)).append("\n");
        status.append("Your dominos: ").append(getPlayerDominos(currentPlayer)).append("\n");
        status.append("Opponent's remaining dominos: ").append(getOpponentRemainingDominos(game, currentPlayer)).append("\n");
        return status.toString();
    }

    public String getLineOfPlay(Game game) {
        StringBuilder lineOfPlay = new StringBuilder();
        List<Domino> dominos = game.getLineOfPlay();
        if (dominos != null) {
            for (Domino domino : dominos) {
                lineOfPlay.append(domino.toString());
            }
        }
        return lineOfPlay.toString();
    }

    private String getPlayerDominos(Player player) {
        StringBuilder playerDominos = new StringBuilder();
        player.getSetofPlayer().forEach(domino -> playerDominos.append(domino.toString()));
        return playerDominos.toString();
    }

    private int getOpponentRemainingDominos(Game game, Player currentPlayer) {
        Player opponent = (currentPlayer.equals(game.getPlayer1())) ? game.getPlayer2() : game.getPlayer1();

        // Collect the elements from the Iterable<Domino> to a List<Domino>
        List<Domino> opponentDominos = StreamSupport.stream(opponent.getSetofPlayer().spliterator(), false)
                .map(obj -> (Domino) obj) // Explicit casting to Domino
                .collect(Collectors.toList());

        return opponentDominos.size();
    }



}










