package server.services;

import com.google.cloud.firestore.CollectionReference;
import database.FirebaseClient;
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
public class GamesService {
    static final String collectionName = "games";

    public String createGame(String userId) throws ExecutionException, InterruptedException {
        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        String gameId =  GameIdGenerator.generateGameId();
        FirebaseClient.addToCollection(collection, new GameId(userId, gameId, 4));
        return gameId;
    }
}
