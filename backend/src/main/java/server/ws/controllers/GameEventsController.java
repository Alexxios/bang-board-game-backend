package server.ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameEventsController {
    private final static String nextMotionUrl = "/next-motion";
    private final static String keepCardUrl = "/keep-card";
    private final static String cardPlayUrl = "/card-play";
    private final static String playerDeathUrl = "/player-death";
    private final static String matchEndUrl = "/match-end";

    @Autowired
    private static SimpMessagingTemplate messagingTemplate;

    public static<T> void nextMotion(String gameId, T message){
        sendToSubscribers(gameId, nextMotionUrl, message);
    }

    public static<T> void keepCard(String gameId, T message){
        sendToSubscribers(gameId, keepCardUrl, message);
    }

    public static<T> void cardPlay(String gameId, T message){
        sendToSubscribers(gameId, cardPlayUrl, message);
    }

    public static<T> void playerDeath(String gameId, T message){
        sendToSubscribers(gameId, playerDeathUrl, message);
    }

    public static<T> void matchEnd(String gameId, T message){
        sendToSubscribers(gameId, matchEndUrl, message);
    }

    private static <T> void sendToSubscribers(String gameId, String url,  T message){
        messagingTemplate.convertAndSendToUser(
                gameId,url,
                message);
    }
}