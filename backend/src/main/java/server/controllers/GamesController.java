package server.controllers;

import jakarta.servlet.http.HttpSession;
import server.beans.UserBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import game_controllers.GameCreator;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class GamesController {

    @GetMapping("/create_game")
    public String createGame(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return GameCreator.createGame(userId);
    }

    @GetMapping("/connect")
    public int connectToGame(@RequestParam(name="gameNumber") int gameNumber){
        return gameNumber;
    }
}
