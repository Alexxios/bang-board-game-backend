package server.services;

import callbacks.CallbackHandlersMapper;
import callbacks.CallbackType;
import cards.Suit;
import characters.Character;
import configurators.CallbacksConfiguration;
import helpers.CharactersGenerator;
import models.callbacks.handlers.ICallbackHandler;
import cards.CardMapper;
import models.PlayingCard;
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
import response.models.*;
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

            if (!game.getCardsForSelection().isEmpty()){
                handlingResult = false;
            }

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

        deleteDeadPlayers(game);

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
            int previousPlayer = game.getMotionPlayerIndex();

            List<PlayingCard> addedCardsCount = game.nextMotion();
            int playerIndex = game.getMotionPlayerIndex();
            PlayingCard firstCard = game.getDeck().getLast();
            while(game.getPlayer(playerIndex).getBuffs().isHasPrison() && firstCard.getSuit() != Suit.Hearts){
                game.getDiscarded().add(firstCard);
                game.getDeck().removeLast();
                firstCard = game.getDeck().getLast();
                addedCardsCount = game.nextMotion();
                playerIndex = game.getMotionPlayerIndex();
            }

            if (game.getPlayer(previousPlayer).getBuffs().isHasDinamite()){
                int currentPlayer = game.getMotionPlayerIndex();
                game.getPlayer(previousPlayer).getBuffs().setHasDinamite(false);
                game.getPlayer(currentPlayer).getBuffs().setHasDinamite(true);

                if (firstCard.getSuit() == Suit.Spades &&
                    2 <= firstCard.getNumber() && firstCard.getNumber() <= 9){
                    game.getPlayer(currentPlayer).getBuffs().setHasDinamite(false);
                    game.getPlayer(currentPlayer).takeDamage(3);
                }

            }

            game.setWasBangPlayed(false);
            gameEventsController.nextMotion(gameId, new NextMotionResult(game.getMotionPlayerIndex()));

            for (PlayingCard card : addedCardsCount){
                gameEventsController.keepCard(gameId, new KeepCard(game.getMotionPlayerIndex(), card));
            }
        }

        deleteDeadPlayers(game);

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

    private void deleteDeadPlayers(GameEntity game){
        int bigSnakePlayerIndex = -1;


        List<Integer> deadPlayers = new ArrayList<>();
        for (int index = 0; index < game.getPlayers().size(); ++index){
            if (game.getPlayers().get(index).getHealth() <= 0){
                deadPlayers.add(index);
                gameEventsController.playerDeath(game.getGameId(), new PlayerDeath(index));
            }else {
                if(game.getPlayer(index).getCharacter() == Character.BigSnake){
                    bigSnakePlayerIndex = index;
                }
            }
        }

        for (int index : deadPlayers){

            if (bigSnakePlayerIndex != -1){
                Player bigSnakePlayer = game.getPlayer(bigSnakePlayerIndex);
                List<PlayingCard> cards = game.getPlayer(index).getCards();
                for (PlayingCard card : cards){
                    bigSnakePlayer.receiveCard(card);
                    gameEventsController.keepCard(game.getGameId(), new KeepCard(bigSnakePlayerIndex, card));
                }
            }

            game.getPlayer(index).setDead(true);
        }

        checkOnFinish(game);
    }

    private void checkOnFinish(GameEntity game){
        int count = 0, winnerIndex = 0;

        for (int index = 0; index < game.getPlayers().size(); ++index){
            if (!game.getPlayer(index).isDead()){
                count++;
                winnerIndex = index;
            }
        }

        if (count == 1){
            gameEventsController.matchEnd(game.getGameId(), new MatchEnd(winnerIndex));
        }

    }
}
