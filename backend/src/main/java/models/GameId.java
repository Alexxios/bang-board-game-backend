package models;

import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

enum GameStatus{
    WaitingPlayers,
    Playing,
    Ended
}

@Getter
public class GameId {

    public GameId() {
        usersNicknames = new ArrayList<>();
        gameStatus = GameStatus.WaitingPlayers;
    }

    public GameId(String owner, String gameId, int maxPlayersCount) {
        this.owner = owner;
        this.gameId = gameId;
        this.maxPlayersCount = maxPlayersCount;
        usersNicknames = new ArrayList<>();
        usersNicknames.add(owner);
        gameStatus = GameStatus.WaitingPlayers;
    }

//    public int getCurrentPlayersCount() {
//        return usersNicknames.size();
//    }

    public void addPlayer(String user) throws FullGame, PlayerAlreadyInGame, CanNotJoinGame {
        if (usersNicknames.size() == maxPlayersCount){
            throw new FullGame();
        }
        if (usersNicknames.contains(user)){
            throw new PlayerAlreadyInGame();
        }
        if (gameStatus != GameStatus.WaitingPlayers){
            throw new CanNotJoinGame();
        }
        usersNicknames.add(user);

        if (usersNicknames.size() == maxPlayersCount){
            gameStatus = GameStatus.Playing;
        }
    }

    private String owner;
    private String gameId;
    private int maxPlayersCount;

    private GameStatus gameStatus;
    private List<String> usersNicknames;
}
