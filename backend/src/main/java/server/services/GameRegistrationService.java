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
import jdk.jshell.Snippet;
import models.GameId;
import models.PlayerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.concurrent.ExecutionException;

class GameIdGenerator{
    static int index = 0;
    static char[] codeLetters = {'a', 'a', 'a', 'a', 'a', 'a'};
    public static String generateGameId(String userId, int playersCount){
        codeLetters[index++]++;
        index %= codeLetters.length;
        String left = String.valueOf(codeLetters[0] + codeLetters[1] + codeLetters[2]);
        String right = String.valueOf(codeLetters[3] + codeLetters[4] + codeLetters[5]);
        return String.valueOf(left + "-" + right);
    }
}


@Service
@CrossOrigin
public class GameRegistrationService {
    static final String collectionName = "gamesInfo";

    public String createGame(String user, int playersCount) throws ExecutionException, InterruptedException {
        final String gameId = GameIdGenerator.generateGameId(user, 4);
        DocumentReference document = FirebaseClient.getDocument(collectionName, gameId);
        GameId game = new GameId(user, gameId, playersCount);
        FirebaseClient.addToDocument(document, game);
        return gameId;
    }

    public void deleteGame(String gameId){
        CollectionReference collectionReference = FirebaseClient.getCollection(collectionName);
        FirebaseClient.deleteDocument(collectionReference, gameId);
    }

    public int connectToGame(String user, String gameId) throws ExecutionException, InterruptedException, PlayerAlreadyInGame, CanNotJoinGame, FullGame, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        DocumentSnapshot document = documentReference.get().get();
        int playersCount;
        if (document.exists()){
            GameId game = document.toObject(GameId.class);
            game.addPlayer(new PlayerId(user));
            documentReference.set(game);
            playersCount = game.getPlayers().size();
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
