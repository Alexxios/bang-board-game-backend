package models;

import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import lombok.Getter;
import models.enums.GameStatus;
import models.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class GameId {

    public GameId() {
        players = new ArrayList<>();
        gameStatus = GameStatus.WaitingPlayers;
    }

    public GameId(String owner, String gameId, int maxPlayersCount) {
        this.owner = owner;
        this.gameId = gameId;
        this.maxPlayersCount = maxPlayersCount;
        players = new ArrayList<>();
        players.add(new PlayerId(owner));
        gameStatus = GameStatus.WaitingPlayers;
    }

    public void addPlayer(PlayerId player) throws FullGame, PlayerAlreadyInGame, CanNotJoinGame {
        if (players.size() == maxPlayersCount){
            throw new FullGame();
        }
        if (players.contains(player)){
            throw new PlayerAlreadyInGame();
        }
        if (gameStatus != GameStatus.WaitingPlayers){
            throw new CanNotJoinGame();
        }
        players.add(player);

        if (players.size() == maxPlayersCount){
            gameStatus = GameStatus.Playing;
        }
    }

    public void changePlayerStatus(String nickname){
        for (PlayerId player : players){
            if (Objects.equals(player.getNickname(), nickname)){
                player.changeStatus();
            }
        }
    }

    public void deleteUser(String nickname){
        for (PlayerId player : players){
            if (Objects.equals(player.getNickname(), nickname)){
                players.remove(player);
                break;
            }
        }
    }

    private String owner;
    private String gameId;
    private int maxPlayersCount;
    private int playersCount;
    private GameStatus gameStatus;
    private List<PlayerId> players;
}
