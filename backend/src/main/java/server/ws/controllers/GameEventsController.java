package server.ws.controllers;
import com.google.cloud.firestore.DocumentReference;
import database.FirebaseClient;
import models.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.concurrent.ExecutionException;

@Service("gameEventsControllerBean")
@Scope("singleton")
public class GameEventsController {
    private final static String nextMotionUrl = "/next-motion";
    private final static String keepCardUrl = "/keep-card";
    private final static String cardPlayUrl = "/card-play";
    private final static String playerDeathUrl = "/player-death";
    private final static String matchEndUrl = "/match-end";

    private final static String selectCardUrl = "/select-card";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FirebaseClient firebaseClient;


    public <T> void nextMotion(GameEntity game, T message){
        sendToSubscribers(game, nextMotionUrl, message);
    }

    public <T> void keepCard(GameEntity game, T message){
        sendToSubscribers(game, keepCardUrl, message);
    }

    public <T> void cardPlay(GameEntity game, T message){
        sendToSubscribers(game, cardPlayUrl, message);
    }

    public <T> void playerDeath(GameEntity game, T message){
        sendToSubscribers(game, playerDeathUrl, message);
    }

    public <T> void matchEnd(GameEntity game, T message){
        sendToSubscribers(game, matchEndUrl, message);
    }

    public <T> void setSelectCard(GameEntity game, T message) {sendToSubscribers(game, selectCardUrl, message);}

    private <T> void sendToSubscribers(GameEntity game, String url,  T message){
        updateDatabase(game);

        messagingTemplate.convertAndSendToUser(
                game.getGameId(), url,
                message);
    }

    private void updateDatabase(GameEntity game) {
        try{
            DocumentReference documentReference = firebaseClient.getDocument("games", game.getGameId());
            firebaseClient.updateDocument(documentReference, game);
        } catch (Exception e) {

        }
    }
}