package server.http.controllers;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import response.models.NicknameCheckResult;
import server.services.UsersService;

import javax.swing.plaf.ComboBoxUI;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("add-user")
    public User addUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        if (usersService.isUserExists(user.getNickname())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return usersService.addUser(user);
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam String nickname) {
        usersService.deleteUser(nickname);
    }

    @GetMapping("check-nickname")
    public NicknameCheckResult checkNickname(@RequestParam String nickname) throws ExecutionException, InterruptedException {
        boolean result = usersService.isUserExists(nickname);
        return new NicknameCheckResult(!result);
    }
}
