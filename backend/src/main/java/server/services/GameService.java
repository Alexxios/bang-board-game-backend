package server.services;

import callbacks.CallbackHandlersMapper;
import callbacks.CallbackType;
import characters.Character;
import configurators.CallbacksConfiguration;
import configurators.ModelsConfiguration;
import helpers.CharactersGenerator;
import models.callbacks.handlers.ICallbackHandler;
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

    private CallbackHandlersMapper callbackHandlersMapper;

    private static final String collectionName = "games";

    private static final String gamesInfoCollectionName = "gamesInfo";

    public void initGame(String gameId) throws ExecutionException, InterruptedException {
        if (callbackHandlersMapper == null){
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CallbacksConfiguration.class);
            this.callbackHandlersMapper = context.getBean("callbackHandlersMapperBean", CallbackHandlersMapper.class);
        }

        CollectionReference collection = FirebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> games = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : games){
            if (Objects.equals(document.getId(), gameId)){
                return;
            }
        }

        DocumentReference gameIdDocumentreference = FirebaseClient.getDocument(gamesInfoCollectionName, gameId);
        GameId gameInfo = gameIdDocumentreference.get().get().toObject(GameId.class);
        int playersCount = gameInfo.getMaxPlayersCount();

        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        List<PlayingCard> cards = CardsGenerator.generateCards();
        List<Role> roles = RolesGenerator.generateRoles(playersCount);
        List<Character> characters = CharactersGenerator.generateCharacters(playersCount);
        List<Player> players = new ArrayList<Player>();

        for(int i = 0; i < playersCount; ++i){
            Player newPlayer = new Player(roles.get(i), characters.get(i));
            for (int j = 0; j < newPlayer.getHealth(); ++j){
                newPlayer.receiveCard(cards.getFirst());
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

        if (!game.getCallbacks().isEmpty()){

            CallbackType callbackType = game.getCallbacks().getFirst().getCallbackType();
            ICallbackHandler callback = callbackHandlersMapper.searchCallback(callbackType);

            if (callback.checkCallback(game, event)){
                callback.positiveAction(game);

                changeMotionPlayerIndexWithCallback(game);
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
        System.out.println(game.getMotionPlayerIndex());
        FirebaseClient.updateDocument(documentReference, game);
        return new EventHandlingResult(handlingResult, event, game);
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {

        DocumentReference documentReference = FirebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        if (!game.getCallbacks().isEmpty()){
            Callback callback = game.getCallbacks().getFirst();
            CallbackType callbackType = callback.getCallbackType();
            ICallbackHandler callbackHandler = callbackHandlersMapper.searchCallback(callbackType);
            callbackHandler.negativeAction(game);

            changeMotionPlayerIndexWithCallback(game);
            gameEventsController.nextMotion(gameId, new NextMotionResult(callback.getEvent().getSenderIndex()));
        } else {
            List<PlayingCard> addedCardsCount = game.nextMotion();

            gameEventsController.nextMotion(gameId, new NextMotionResult(game.getMotionPlayerIndex()));

            for (PlayingCard card : addedCardsCount){
                gameEventsController.keepCard(gameId, new KeepCard(game.getMotionPlayerIndex(), card));
            }
        }

        FirebaseClient.updateDocument(documentReference, game);
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

    private void changeMotionPlayerIndexWithCallback(GameEntity game){
        if (game.getCallbacks().size() == 1){
            game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getSenderIndex());
            game.resetCallback();
        } else {
            game.resetCallback();
            game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        }
    }

}
