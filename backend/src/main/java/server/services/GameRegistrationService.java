package server.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import database.FirebaseClient;
import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.GameDoesNotExist;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import models.GameId;
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
    static final String collectionName = "games";

    public String createGame(String userId) throws ExecutionException, InterruptedException {
        final String gameId = GameIdGenerator.generateGameId();
        DocumentReference document = FirebaseClient.getDocument(collectionName, gameId);
        GameId game = new GameId(userId, gameId, 4);
        FirebaseClient.addToDocument(document, game);
        return gameId;
    }

    public String connectToGame(String userId, String gameId) throws ExecutionException, InterruptedException, PlayerAlreadyInGame, CanNotJoinGame, FullGame, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        DocumentSnapshot document = documentReference.get().get();
        if (document.exists()){
            GameId game = document.toObject(GameId.class);
            game.addPlayer(userId);
            documentReference.set(game);
        }else{
            throw new GameDoesNotExist();
        }

        return gameId;
    }
}
