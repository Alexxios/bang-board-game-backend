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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import response.models.EventHandlingResult;
import response.models.KeepCard;
import response.models.NextMotionResult;
import response.models.OnCardPlay;
import server.ws.controllers.GameEventsController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    @Autowired
    private GameEventsController gameEventsController;

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
        List<Role> roles = RolesGenerator.generateRoles(2);
        List<Player> players = new ArrayList<Player>();

        for(Role role : roles){
            Player newPlayer = new Player(role);
            for (int i = 0; i < newPlayer.getHealth(); ++i){
                newPlayer.getCard(cards.getFirst());
                cards.removeFirst();
            }
            players.add(newPlayer);
        }

        GameEntity game = new GameEntity(0, players, cards, gameId);
        documentReference.set(game);
    }

    public EventHandlingResult handleEvent(String gameId, Event event) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        HandleEventResult result;
        boolean handlingResult = true;

        if (game.getCallback().isActive()){
            CallbackType callbackType = game.getCallback().getCallbackType();
            ICallbackHandler callback = CallbackHandlersMapper.searchCallback(callbackType);

            if (callback.checkCallback(event)){
                callback.positiveAction(game);
                game.setMotionPlayerIndex(game.getCallback().getEvent().getSenderIndex());
                resetCallback(game);
            }else{
                handlingResult = false;
            }
        }else{
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CardMapper.class);

            CardMapper cardMapper = context.getBean("cardMapperBean", CardMapper.class);

            ICard card = cardMapper.searchCard(event.getCardDescription());
            result = card.handlerEvent(game, event);
            game = result.game();
            handlingResult = result.isSuccessful();
        }

        if (handlingResult){
            game.getPlayer(event.getSenderIndex()).getCards().remove(event.getCardIndex());
            gameEventsController.cardPlay(gameId, new OnCardPlay(event.getSenderIndex(), event.getCardIndex()));
        }

        if (game.getMotionPlayerIndex() != event.getSenderIndex()){
            gameEventsController.nextMotion(game.getGameId(), new NextMotionResult(game.getMotionPlayerIndex()));
        }
        FirebaseClient.updateDocument(documentReference, game);
        return new EventHandlingResult(handlingResult, event, game);
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {

        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        if (game.getCallback().isActive()){
            CallbackType callbackType = game.getCallback().getCallbackType();
            ICallbackHandler callback = CallbackHandlersMapper.searchCallback(callbackType);
            callback.negativeAction(game);
            resetCallback(game);
        }

        List<PlayingCard> addedCardsCount = game.nextMotion();

        gameEventsController.nextMotion(gameId, new NextMotionResult(game.getMotionPlayerIndex()));

        for (PlayingCard card : addedCardsCount){
            gameEventsController.keepCard(gameId, new KeepCard(game.getMotionPlayerIndex(), card));
        }

        FirebaseClient.updateDocument(documentReference, game);
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
