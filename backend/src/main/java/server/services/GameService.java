package server.services;

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
import models.cards.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    private static final String gamesCollectionName = "games";

    public void initGame(String gameId) {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        List<Card> cards = CardsGenerator.generateCards();
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
        Card card = CardMapper.searchCard(event.getCardDescription());
        GameEntity newGameEntity = card.handlerEvent(game, event);
        documentReference.set(newGameEntity);
        return newGameEntity;
    }

    public GameEntity nextMotion(String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        DocumentReference documentReference = FirebaseClient.getDocument(gamesCollectionName, gameId);
        GameEntity game = getGameEntity(documentReference);
        game.nextMotion();
        return game;
    }

    private GameEntity getGameEntity(DocumentReference documentReference) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentSnapshot document = documentReference.get().get();
        if (!document.exists()){
            throw new GameDoesNotExist();
        }

        return document.toObject(GameEntity.class);
    }
}
