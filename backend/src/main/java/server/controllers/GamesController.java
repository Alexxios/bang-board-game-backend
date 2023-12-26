package server.controllers;
import org.springframework.web.bind.annotation.*;
import server.services.GamesService;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class GamesController {

    private GamesService gamesService;

    public GamesController(GamesService gamesService){
        this.gamesService = gamesService;
    }

    @PostMapping("/create_game")
    public String createGame(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return gamesService.createGame(userId);
    }

    @GetMapping("/connect")
    public int connectToGame(@RequestParam(name="gameNumber") int gameNumber){
        return gameNumber;
    }
}
