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
    public GameId(String ownerId, String gameId, int maxPlayersCount) {
        this.ownerId = ownerId;
        this.gameId = gameId;
        this.maxPlayersCount = maxPlayersCount;
        playersIds = new ArrayList<String>();
        playersIds.add(ownerId);
        gameStatus = GameStatus.WaitingPlayers;
    }

    public int getCurrentPlayersCount() {
        return playersIds.size();
    }

    public void addPlayer(String playerId) throws FullGame, PlayerAlreadyInGame, CanNotJoinGame {
        if (playersIds.size() == maxPlayersCount){
            throw new FullGame();
        }
        if (playersIds.contains(playerId)){
            throw new PlayerAlreadyInGame();
        }
        if (gameStatus != GameStatus.WaitingPlayers){
            throw new CanNotJoinGame();
        }
        playersIds.add(playerId);

        if (playersIds.size() == maxPlayersCount){
            gameStatus = GameStatus.Playing;
        }
    }

    private String ownerId;
    private String gameId;
    private int maxPlayersCount;

    private GameStatus gameStatus;
    private List<String> playersIds;
}
