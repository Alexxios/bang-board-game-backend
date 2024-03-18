package server.ws.controllers;

import com.google.cloud.firestore.DocumentReference;
import database.FirebaseClient;
import models.GameId;
import models.PlayerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import request.models.WaitingRoomMessage;
import server.services.GameRegistrationService;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class WaitingRoomController {
    public WaitingRoomController(GameRegistrationService gamesService){
        this.gamesService = gamesService;
    }
    private final GameRegistrationService gamesService;
    private final String collectionName = "gamesInfo";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FirebaseClient firebaseClient;

    @MessageMapping("/leave-game")
    public void leaveGame(@Payload WaitingRoomMessage message) throws ExecutionException, InterruptedException {
        GameId game = gamesService.getGame(message.gameId());
        game.deleteUser(message.nickname());

        DocumentReference documentReference = firebaseClient.getDocument(collectionName, message.gameId());
        firebaseClient.updateDocument(documentReference, game);

        sendToSubscribers(message.gameId(), game.getPlayers());
    }

    @MessageMapping("/change-player-status")
    public void changePlayerStatus(@Payload WaitingRoomMessage message) throws ExecutionException, InterruptedException {
        GameId game = gamesService.getGame(message.gameId());
        game.changePlayerStatus(message.nickname());

        DocumentReference documentReference = firebaseClient.getDocument(collectionName, message.gameId());
        firebaseClient.updateDocument(documentReference, game);

        sendToSubscribers(message.gameId(), game.getPlayers());
    }

    @MessageMapping("/game-connection")
    public void userConnect(@Payload WaitingRoomMessage message) throws ExecutionException, InterruptedException {
        List<PlayerId> players = gamesService.getGame(message.gameId()).getPlayers();

        sendToSubscribers(message.gameId(), players);
    }


    private <T> void sendToSubscribers(String gameId, T message){
        messagingTemplate.convertAndSendToUser(
                gameId,"/connected-users",
                message);
    }
}