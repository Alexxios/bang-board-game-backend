package server.ws.controllers;
import com.google.cloud.firestore.DocumentReference;
import database.FirebaseClient;
import models.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import response.models.*;

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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FirebaseClient firebaseClient;


    public void nextMotion(GameEntity game, NextMotionResult message){
        sendToSubscribers(game, nextMotionUrl, message);
    }

    public void keepCard(GameEntity game, KeepCard message){
        sendToSubscribers(game, keepCardUrl, message);
    }

    public void cardPlay(GameEntity game, OnCardPlay message){
        sendToSubscribers(game, cardPlayUrl, message);
    }

    public void playerDeath(GameEntity game, PlayerDeath message){
        sendToSubscribers(game, playerDeathUrl, message);
    }

    public void matchEnd(GameEntity game, MatchEnd message){
        sendToSubscribers(game, matchEndUrl, message);
    }

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