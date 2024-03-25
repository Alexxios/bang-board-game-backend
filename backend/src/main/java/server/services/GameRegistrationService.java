package server.services;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import database.FirebaseAuth;
import database.FirebaseClient;
import exceptions.game_exceptions.CanNotJoinGame;
import exceptions.game_exceptions.FullGame;
import exceptions.game_exceptions.GameDoesNotExist;
import exceptions.game_exceptions.PlayerAlreadyInGame;
import models.GameId;
import models.PlayerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.concurrent.CancellationException;
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

    private static final Logger logger = LoggerFactory.getLogger(GameRegistrationService.class);

    @Autowired
    private FirebaseClient firebaseClient;

    public String createGame(String user, int playersCount) {
        final String gameId = GameIdGenerator.generateGameId(user, 4);
        DocumentReference document = firebaseClient.getDocument(collectionName, gameId);
        GameId game = new GameId(user, gameId, playersCount);
        firebaseClient.addDocument(document, game);
        return gameId;
    }

    public void deleteGame(String gameId){
        CollectionReference collectionReference = firebaseClient.getCollection(collectionName);
        firebaseClient.deleteDocument(collectionReference, gameId);
    }

    public int connectToGame(String user, String gameId) throws PlayerAlreadyInGame, CanNotJoinGame, FullGame, GameDoesNotExist {
        DocumentReference documentReference = firebaseClient.getDocument(collectionName, gameId);

        try{
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
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
        return 0;
    }

    public GameId getGame(String gameId) {
        CollectionReference collection = firebaseClient.getCollection(collectionName);
        try{
            List<QueryDocumentSnapshot> games = collection.get().get().getDocuments();
            for (QueryDocumentSnapshot document : games){
                GameId game = document.toObject(GameId.class);
                if (Objects.equals(game.getGameId(), gameId)){
                    return game;
                }
            }
        } catch (InterruptedException e){
            logger.error("Firebase request was interrupted. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (CancellationException e){
            logger.error("Firebase request was cancelled, please check your database. Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } catch (ExecutionException e){
            logger.error("Firebase request was interrupted while execution, please check your database.  Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }


        return new GameId();
    }
}
