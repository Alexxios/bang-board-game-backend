package server.services;

import callbacks.CallbackHandlersMapper;
import callbacks.CallbackType;
import cards.Suit;
import characters.Character;
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

    @Autowired
    private FirebaseClient firebaseClient;

    @Autowired
    private CallbackHandlersMapper callbackHandlersMapper;

    @Autowired
    private CardsGenerator cardsGenerator;
    @Autowired
    private RolesGenerator rolesGenerator;
    @Autowired
    private CharactersGenerator charactersGenerator;
    @Autowired
    private CardMapper cardMapper;

    private static final String collectionName = "games";

    private static final String gamesInfoCollectionName = "gamesInfo";

    public void initGame(String gameId) throws ExecutionException, InterruptedException {

        CollectionReference collection = firebaseClient.getCollection(collectionName);
        List<QueryDocumentSnapshot> games = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : games){
            if (Objects.equals(document.getId(), gameId)){
                return;
            }
        }

        DocumentReference gameIdDocumentreference = firebaseClient.getDocument(gamesInfoCollectionName, gameId);
        GameId gameInfo = gameIdDocumentreference.get().get().toObject(GameId.class);
        int playersCount = gameInfo.getMaxPlayersCount();

        DocumentReference documentReference = firebaseClient.getDocument(collectionName, gameId);
        List<PlayingCard> cards = cardsGenerator.generateCards();
        List<Role> roles = rolesGenerator.generateRoles(playersCount);
        List<Character> characters = charactersGenerator.generateCharacters(playersCount);
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
        DocumentReference documentReference = firebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        HandleEventResult result;
        boolean handlingResult = true;
        int senderIndex = event.getSenderIndex();

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
            ICard card = cardMapper.searchCard(event.getCardDescription());
            result = card.handlerEvent(game, event);
            game = result.game();
            handlingResult = result.isSuccessful();
        }

        if (handlingResult){
            game.getPlayer(senderIndex).getCards().remove(event.getCardIndex());

            // Susie Lafayette Ability
            if (game.getPlayer(senderIndex).getCharacter() == Character.SuzyLafayette
                    && game.getPlayer(senderIndex).getCards().isEmpty()) {
                game.getPlayer(senderIndex).receiveCard(game.drawFirstCard());
            }

            gameEventsController.cardPlay(game, new OnCardPlay(senderIndex, event.getCardIndex()));
        }

        if (game.getMotionPlayerIndex() != senderIndex){
            gameEventsController.nextMotion(game, new NextMotionResult(game.getMotionPlayerIndex()));
        }

        deleteDeadPlayers(game);
        return new EventHandlingResult(handlingResult, event, game);
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {

        DocumentReference documentReference = firebaseClient.getDocument(collectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        if (!game.getCallbacks().isEmpty()){
            Callback callback = game.getCallbacks().getFirst();
            CallbackType callbackType = callback.getCallbackType();
            ICallbackHandler callbackHandler = callbackHandlersMapper.searchCallback(callbackType);
            callbackHandler.negativeAction(game);

            changeMotionPlayerIndexWithCallback(game);
            gameEventsController.nextMotion(game, new NextMotionResult(callback.getEvent().getSenderIndex()));
        } else {
            List<PlayingCard> addedCardsCount = setNextMotion(game);

            game.setWasBangPlayed(false);

            gameEventsController.nextMotion(game, new NextMotionResult(game.getMotionPlayerIndex()));
            for (PlayingCard card : addedCardsCount){
                gameEventsController.keepCard(game, new KeepCard(game.getMotionPlayerIndex(), card));
            }
        }

        deleteDeadPlayers(game);
        return game;
    }


    public GameEntity getGame(String gameId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firebaseClient.getDocument(collectionName, gameId);
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
        Event event = game.getCallbacks().getFirst().getEvent();
        int senderIndex = event.getSenderIndex(), getterIndex = event.getGetterIndex();
        if (game.getCallbacks().size() == 1){
            game.setMotionPlayerIndex(senderIndex);
            game.resetCallback();
        } else {
            game.resetCallback();
            game.setMotionPlayerIndex(getterIndex);
        }
    }

    private List<PlayingCard> setNextMotion(GameEntity game){
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


                // Bart Cassidy Ability
                if (game.getPlayer(currentPlayer).getHealth() > 0
                        && game.getPlayer(currentPlayer).getCharacter() == Character.BartCassidy) {
                    game.getPlayer(currentPlayer).receiveCard(game.drawFirstCard());
                }
            }
        }

        return addedCardsCount;
    }

    private void deleteDeadPlayers(GameEntity game){
        int bigSnakePlayerIndex = -1;
        String gameId = game.getGameId();

        List<Integer> deadPlayers = new ArrayList<>();
        for (int index = 0; index < game.getPlayers().size(); ++index){
            if (game.getPlayers().get(index).getHealth() <= 0){
                deadPlayers.add(index);
                gameEventsController.playerDeath(game, new PlayerDeath(index));
            }else {
                if(game.getPlayer(index).getCharacter() == Character.VultureSam){
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
                    gameEventsController.keepCard(game, new KeepCard(bigSnakePlayerIndex, card));
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
            gameEventsController.matchEnd(game, new MatchEnd(winnerIndex));
        }

    }
}
