package com.dominogame.backend.GameService;


import com.dominogame.backend.model.*;
import com.dominogame.backend.model.Domino;
import com.dominogame.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class GameService {

    private ArrayList<Domino> unplayedDominoes = new ArrayList<>();
    private ArrayList<Domino> setofPlayer1 = new ArrayList<>();
    private ArrayList<Domino> setofPlayer2 = new ArrayList<>();
    private ArrayList<Domino> lineofPlay = new ArrayList<>();
    private ArrayList<Domino> setofPlayer = null;

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    Scanner sc =new Scanner(System.in);
//    @Transactional
//    public Game createGame(Player player) {
//        Game game = new Game();
//        game.setGameId(UUID.randomUUID().toString());
//        game.setPlayer1(player);
//        game.setStatus(GameStatus.NEW); // Assuming NEW is an enum value
//        // Optionally, set other properties if needed
//
//        // Save the game entity to the database
//        return gameRepository.save(game);
//    }
    @Transactional
    public Game connectToGame(Player player) {
        // Find a game with status NEW
        Optional<Game> optionalGame = gameRepository.findFirstByStatus(GameStatus.NEW);

        // If no game with status NEW is found, create a new game
        Game game = optionalGame.orElseGet(() -> {
            Game newGame = new Game();
            newGame.setPlayer1(player);
            newGame.setGameId(UUID.randomUUID().toString());
            newGame.setStatus(GameStatus.NEW);
            return newGame;
        });

        // Update the game with player2 and change status to IN_PROGRESS

        if (game.getPlayer1() != null && game.getPlayer2() == null) {
            // Set the second player
            game.setPlayer2(player);
            game.setStatus(GameStatus.IN_PROGRESS);
        }

        // Save the game entity to the database
        return gameRepository.save(game);
    }



    @Transactional
    public Game gamePlay(Game game) {
        Integer gameId=Integer.parseInt(game.getGameId());

        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()){
            Game retrievedGame = optionalGame.get();
        Domino x = null, y = null, z = null;
        String[] choice;
        int index;
        char side;
        String str ="";
        String userInput;
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

        while(true) {
            if (!canPlay(setofPlayer)) {
                if (!unplayedDominoes.isEmpty())
                    moveDominoRandomly(unplayedDominoes, setofPlayer);
            }
            while (true) {
                userInput = sc.nextLine();

                if(userInput.equals("je passe"))
                    break;
                choice = userInput.split(" ");
                index = Integer.parseInt(choice[0])-1;
                side = choice[1].charAt(0);
                if((index < setofPlayer.size()) && (side == 'g' || side == 'd') && isValidMove(setofPlayer.get(index), side)){
                    moveDominotoLineofPlay(setofPlayer, index, side);
                    break;
                }
                else {
                    str = "Votre choix n'est pas valide.";
                }
            }
            if(setofPlayer.isEmpty() || isGameFinished()){
                String[] res = getScore(getWeight(setofPlayer1),getWeight(setofPlayer2));
                break;
            }

        }
        gameRepository.save(game);
        }
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
            if ((d.getLeft() == d.getRight()) && (d.getWeight() > r.getWeight()))
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
            if(lineofPlay.get(0).getLeft() != d.getRight())
                d.reverse();
            lineofPlay.add(0, d);
        }
        else{
            if(lineofPlay.get(lineofPlay.size()-1).getRight() != d.getLeft())
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
            return((lineofPlay.get(0).getLeft() == d.getRight()) || (lineofPlay.get(0).getLeft() == d.getLeft()));
        else
            return((lineofPlay.get(lineofPlay.size()-1).getRight() == d.getRight()) || (lineofPlay.get(lineofPlay.size()-1).getRight() == d.getLeft()));
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
    public void leaveGame(String gameId,Player currentPlayer) {
        // Find the game by its gameId
        Optional<Game> optionalGame = gameRepository.findById(Integer.valueOf(gameId));
        // If the game exists, remove the player from the game
        optionalGame.ifPresent(game -> {
            //assuming player2 wants to leave:
            if (currentPlayer.equals(game.getPlayer1())) {
                game.setPlayer1(null);
            } else if (currentPlayer.equals(game.getPlayer2())) {
                game.setPlayer2(null);
            }
            Player winnerPlayer =(game.getPlayer1() != null) ? game.getPlayer1() : game.getPlayer2();
            game.setWinner(winnerPlayer);
            // Update the game status or perform any other necessary cleanup
            game.setStatus(GameStatus.FINISHED); // Set to some appropriate status
            // Save the updated game entity
            gameRepository.save(game);
        });
    }
}


