package server.ws.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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


    public <T> void nextMotion(String gameId, T message){
        sendToSubscribers(gameId, nextMotionUrl, message);
    }

    public <T> void keepCard(String gameId, T message){
        sendToSubscribers(gameId, keepCardUrl, message);
    }

    public <T> void cardPlay(String gameId, T message){
        sendToSubscribers(gameId, cardPlayUrl, message);
    }

    public <T> void playerDeath(String gameId, T message){
        sendToSubscribers(gameId, playerDeathUrl, message);
    }

    public <T> void matchEnd(String gameId, T message){
        sendToSubscribers(gameId, matchEndUrl, message);
    }

    private <T> void sendToSubscribers(String gameId, String url,  T message){
        messagingTemplate.convertAndSendToUser(
                gameId,url,
                message);
    }
}