package server.controllers;
import exceptions.game_exceptions.*;
import models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public String createGame(@RequestBody User user) throws ExecutionException, InterruptedException {
        return gamesService.createGame(user);
    }

    @GetMapping("connect")
    public User connectToGame(@RequestParam User user, String gameId) throws ExecutionException, InterruptedException {
        try{
            gamesService.connectToGame(user, gameId);
        } catch (GameException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return user;
    }
}
