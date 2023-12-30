package server.services;

import callbacks.CallbackMapper;
import callbacks.CallbackType;
import callbacks.ICallback;
import cards.CardMapper;
import cards.Role;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import database.FirebaseClient;
import exceptions.game_exceptions.GameDoesNotExist;
import helpers.CardsGenerator;
import helpers.RolesGenerator;
import models.Event;
import models.GameEntity;
import models.Player;
import models.cards.ICard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    private static final String gamesCollectionName = "games";

    public void initGame(String gameId) {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        List<ICard> cards = CardsGenerator.generateCards();
        List<Role> roles = RolesGenerator.generateRoles(4);
        List<Player> players = new ArrayList<Player>();

        for(Role role : roles){
            players.add(new Player(role));
        }

        GameEntity game = new GameEntity(0, players, cards);
        documentReference.set(game);
    }

    public GameEntity handleEvent(String gameId, Event event) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        GameEntity game = getGameEntity(documentReference);

        if (game.getCallback() != null && game.getCallback().isActive()){
            CallbackType callbackType = game.getCallback().getCallbackType();
            ICallback callback = CallbackMapper.searchCallback(callbackType);
            if (callback.checkCallback(event)){
                callback.positiveAction(game);
            }else{
                callback.negativeAction(game);
            }
            game.setMotionPlayerIndex(game.getCallback().getCallbackPlayerId());
            resetCallback(game);
        }else{
            ICard card = CardMapper.searchCard(event.getCardDescription());
            card.handlerEvent(game, event);
        }

        documentReference.set(game);
        return game;
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        game.nextMotion();
        return game;
    }

    public GameEntity resetCallback(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        resetCallback(game);
        documentReference.set(game);
        return game;
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
