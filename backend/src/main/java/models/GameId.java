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
    public GameId(User owner, String gameId, int maxPlayersCount) {
        this.owner = owner;
        this.gameId = gameId;
        this.maxPlayersCount = maxPlayersCount;
        usersIds = new ArrayList<>();
        usersIds.add(owner);
        gameStatus = GameStatus.WaitingPlayers;
    }

    public int getCurrentPlayersCount() {
        return usersIds.size();
    }

    public void addPlayer(User user) throws FullGame, PlayerAlreadyInGame, CanNotJoinGame {
        if (usersIds.size() == maxPlayersCount){
            throw new FullGame();
        }
        if (usersIds.contains(user)){
            throw new PlayerAlreadyInGame();
        }
        if (gameStatus != GameStatus.WaitingPlayers){
            throw new CanNotJoinGame();
        }
        usersIds.add(user);

        if (usersIds.size() == maxPlayersCount){
            gameStatus = GameStatus.Playing;
        }
    }

    private User owner;
    private String gameId;
    private int maxPlayersCount;

    private GameStatus gameStatus;
    private List<User> usersIds;
}
