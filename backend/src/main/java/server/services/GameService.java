package server.services;

import cards.CardMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import database.FirebaseClient;
import exceptions.game_exceptions.GameDoesNotExist;
import models.Event;
import models.GameEntity;
import models.cards.Card;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class GameService {
    public void handleEvent(String gameId, Event event) throws ExecutionException, InterruptedException, GameDoesNotExist {
        DocumentReference documentReference = FirebaseClient.getDocument("games", gameId);
        DocumentSnapshot document = documentReference.get().get();
        if (!document.exists()){
            throw new GameDoesNotExist();
        }

        GameEntity game = document.toObject(GameEntity.class);
        Card card = CardMapper.searchCard(event.getCardDescription());
        card.handlerEvent(game, event);
    }
}
