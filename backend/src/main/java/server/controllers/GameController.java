package server.controllers;

import exceptions.game_exceptions.GameDoesNotExist;
import models.Event;
import org.springframework.web.bind.annotation.*;
import server.services.GameService;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping("handler-event")
    public void handleEvent(@RequestParam String gameId, @RequestBody Event event) throws GameDoesNotExist, ExecutionException, InterruptedException {
        gameService.handleEvent(gameId, event);
    }
}
