package server.http.controllers;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import response.models.NicknameCheckResult;
import server.services.UsersService;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("add-user/{nickname}")
    public void addUser(@PathVariable String nickname) {
        if (usersService.isUserExists(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        usersService.addUser(nickname);
    }

    @DeleteMapping("/delete-user/{nickname}")
    public void deleteUser(@PathVariable String nickname) {
        usersService.deleteUser(nickname);
    }

    @GetMapping("check-nickname")
    public NicknameCheckResult checkNickname(@RequestParam String nickname) {
        boolean result = usersService.isUserExists(nickname);
        return new NicknameCheckResult(result);
    }
}
