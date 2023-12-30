package server.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import database.FirebaseClient;
import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.GameDoesNotExist;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import models.GameId;
import models.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

class GameIdGenerator{
    private static int gamesCount = 0;

    public static String generateGameId(){
        ++gamesCount;
        return String.valueOf(gamesCount);
    }
}


@Service
public class GameRegistrationService {
    static final String collectionName = "gamesInfo";

    public String createGame(User user) throws ExecutionException, InterruptedException {
        final String gameId = GameIdGenerator.generateGameId();
        DocumentReference document = FirebaseClient.getDocument(collectionName, gameId);
        GameId game = new GameId(user, gameId, 4);
        FirebaseClient.addToDocument(document, game);
        return gameId;
    }

    public int connectToGame(User user, String gameId) throws ExecutionException, InterruptedException, PlayerAlreadyInGame, CanNotJoinGame, FullGame, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        DocumentSnapshot document = documentReference.get().get();
        int playersCount;
        if (document.exists()){
            GameId game = document.toObject(GameId.class);
            game.addPlayer(user);
            documentReference.set(game);
            playersCount = game.getCurrentPlayersCount();
        }else{
            throw new GameDoesNotExist();
        }

        return playersCount;
    }
}
