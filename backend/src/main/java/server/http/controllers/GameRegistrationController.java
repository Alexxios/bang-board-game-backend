package server.http.controllers;
import exceptions.game_exceptions.*;
import models.GameId;
import models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import response.models.EnterGameResult;
import server.services.GameRegistrationService;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class GameRegistrationController {

    private final GameRegistrationService gamesService;

    public GameRegistrationController(GameRegistrationService gamesService){
        this.gamesService = gamesService;
    }

    @PostMapping("create-game")
    public String createGame(@RequestBody String user) throws ExecutionException, InterruptedException {
        return gamesService.createGame(user);
    }

    @GetMapping("enter-the-game/{user}/{gameId}")
    public EnterGameResult enterTheGame(@PathVariable("user") String user, @PathVariable("gameId") String gameId) throws ExecutionException, InterruptedException {
        try{
            gamesService.connectToGame(user, gameId);
        } catch (GameException e){
            return new EnterGameResult(false);
        }

        return new EnterGameResult(true);
    }

    @DeleteMapping("/delete-game")
    public void deleteGame(@RequestParam String gameId) {
        gamesService.deleteGame(gameId);
    }

    @GetMapping("get-game")
    public GameId getGame(@RequestParam String gameId) throws ExecutionException, InterruptedException {
        return gamesService.getGame(gameId);
    }
}
