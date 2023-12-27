package server.controllers;
import exceptions.game_exceptions.*;
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

    @PostMapping("/create_game")
    public String createGame(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return gamesService.createGame(userId);
    }

    @GetMapping("/connect")
    public String connectToGame(@RequestParam String userId, String gameId) throws GameDoesNotExist, PlayerAlreadyInGame, CanNotJoinGame, FullGame, ExecutionException, InterruptedException {
        try{
            gamesService.connectToGame(userId, gameId);
        } catch (GameException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userId;
    }
}
