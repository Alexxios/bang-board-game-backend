package server.services;

import callbacks.CallbackHandlersMapper;
import callbacks.CallbackType;
import callbacks.handlers.ICallbackHandler;
import cards.CardMapper;
import cards.PlayingCard;
import cards.Role;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import database.FirebaseClient;
import exceptions.game_exceptions.GameDoesNotExist;
import helpers.CardsGenerator;
import helpers.RolesGenerator;
import models.*;
import models.cards.playing.ICard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    private static final String collectionName = "games";

    public void initGame(String gameId) throws ExecutionException, InterruptedException {

        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> games = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : games){
            if (Objects.equals(document.getId(), gameId)){
                return;
            }
        }

        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        List<PlayingCard> cards = CardsGenerator.generateCards();
        List<Role> roles = RolesGenerator.generateRoles(4);
        List<Player> players = new ArrayList<Player>();

        for(Role role : roles){
            players.add(new Player(role));
        }

        GameEntity game = new GameEntity(0, players, cards);
        documentReference.set(game);
    }

    public GameEntity handleEvent(String gameId, Event event) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        if (game.getCallback().isActive()){
            CallbackType callbackType = game.getCallback().getCallbackType();
            ICallbackHandler callback = CallbackHandlersMapper.searchCallback(callbackType);

            if (callback.checkCallback(event)){
                callback.positiveAction(game);
            }else{
                callback.negativeAction(game);
            }
            game.setMotionPlayerIndex(game.getCallback().getEvent().getSenderIndex());
            resetCallback(game);
        }else{
            ICard card = CardMapper.searchCard(event.getCardDescription());
            card.handlerEvent(game, event);
        }

        documentReference.set(game);
        return game;
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        game.nextMotion();
        return game;
    }

    public GameEntity resetCallback(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        CallbackType callbackType = game.getCallback().getCallbackType();
        ICallbackHandler callback = CallbackHandlersMapper.searchCallback(callbackType);
        callback.negativeAction(game);

        resetCallback(game);
        documentReference.set(game);
        return game;
    }

    public GameEntity getGame(String gameId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();
        return documentSnapshot.get().toObject(GameEntity.class);
    }

    private GameEntity getGameEntity(DocumentReference documentReference) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentSnapshot document = documentReference.get().get();
        if (!document.exists()){
            throw new GameDoesNotExist();
        }

        return document.toObject(GameEntity.class);
    }

    private void resetCallback(GameEntity game) {
        game.getCallback().reset();
    }
}
