package server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import game_controllers.GameCreator;

@RestController
public class GamesController {
    @GetMapping("/create_game")
    public int createGame(){
        int gameNumber = GameCreator.createGame();
        return gameNumber;
    }

    @GetMapping("/connect")
    public int connectToGame(@RequestParam(name="gameNumber") int gameNumber){
        return gameNumber;
    }
}
