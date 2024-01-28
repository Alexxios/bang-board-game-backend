package server.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import database.FirebaseClient;
import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.GameDoesNotExist;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import models.GameId;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

class GameIdGenerator{
    private static int gamesCount = 0;

    public static String generateGameId(){
        ++gamesCount;
        return String.valueOf(gamesCount);
    }
}


@Service
@CrossOrigin
public class GameRegistrationService {
    static final String collectionName = "gamesInfo";

    public String createGame(String user) throws ExecutionException, InterruptedException {
        final String gameId = GameIdGenerator.generateGameId();
        DocumentReference document = FirebaseClient.getDocument(collectionName, gameId);
        GameId game = new GameId(user, gameId, 4);
        FirebaseClient.addToDocument(document, game);
        return gameId;
    }

    public int connectToGame(String user, String gameId) throws ExecutionException, InterruptedException, PlayerAlreadyInGame, CanNotJoinGame, FullGame, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        DocumentSnapshot document = documentReference.get().get();
        int playersCount;
        if (document.exists()){
            GameId game = document.toObject(GameId.class);
            game.addPlayer(user);
            documentReference.set(game);
            playersCount = game.getUsersNicknames().size();
        }else{
            throw new GameDoesNotExist();
        }

        return playersCount;
    }

    public GameId getGame(String gameId) throws ExecutionException, InterruptedException {
        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> games = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : games){
            GameId game = document.toObject(GameId.class);
            if (Objects.equals(game.getGameId(), gameId)){
                return game;
            }
        }

        return new GameId();
    }
}
