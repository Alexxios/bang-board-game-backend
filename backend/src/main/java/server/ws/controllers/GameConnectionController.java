package server.ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import request.models.ConnectionMessage;
import server.services.GameRegistrationService;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class GameConnectionController {

    private final GameRegistrationService gamesService;

    public GameConnectionController(GameRegistrationService gamesService){
        this.gamesService = gamesService;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game-connection")
    public ConnectionMessage userConnect(@Payload ConnectionMessage message) throws ExecutionException, InterruptedException {
        List<String> players = gamesService.getGame(message.gameId()).getUsersNicknames();

        messagingTemplate.convertAndSendToUser(
                message.gameId(),"/connected-users",
                players);
        return message;
    }

}