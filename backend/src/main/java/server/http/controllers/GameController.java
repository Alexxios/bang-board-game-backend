package server.http.controllers;

import exceptions.game_exceptions.GameDoesNotExist;
import models.Event;
import models.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import response.models.EventHandlingResult;
import server.services.GameService;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping("init-game")
    public void initGame(@RequestBody String gameId) throws ExecutionException, InterruptedException {
        gameService.initGame(gameId);
    }

    @PostMapping("handle-event")
    public EventHandlingResult handleEvent(@RequestParam String gameId, @RequestBody Event event) throws GameDoesNotExist, ExecutionException, InterruptedException {
        return gameService.handleEvent(gameId, event);
    }

    @PostMapping("next-motion")
    public GameEntity nextMotion(@RequestBody String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        return gameService.nextMotion(gameId);
    }

    @PostMapping("reset-callback")
    public GameEntity resetCallback(@RequestParam String gameId) throws GameDoesNotExist, ExecutionException, InterruptedException {
        return gameService.resetCallback(gameId);
    }

    @GetMapping("/get-game")
    public GameEntity getGame(@RequestParam String gameId) throws ExecutionException, InterruptedException {
        return gameService.getGame(gameId);
    }
}
