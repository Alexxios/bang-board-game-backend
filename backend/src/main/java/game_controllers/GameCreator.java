package game_controllers;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import database.FirebaseClient;
import models.GameId;

import java.util.concurrent.ExecutionException;

class GameIdGenerator{
    private static int gamesCount = 0;

    public static String generateGameId(){
        ++gamesCount;
        return String.valueOf(gamesCount);
    }
}

public class GameCreator {
    static final String collectionName = "games";

    public static String createGame(String userId) throws ExecutionException, InterruptedException {
        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        String gameId =  GameIdGenerator.generateGameId();
        FirebaseClient.addToCollection(collection, new GameId(userId, gameId, 4));
        return gameId;
    }
}
