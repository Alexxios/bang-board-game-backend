package server.controllers;

import models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import server.services.UsersService;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("add-user")
    public String addUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        if (usersService.isUserExists(userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return usersService.addUser(userId);
    }

    @GetMapping("find-user")
    public boolean findUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return usersService.isUserExists(userId);
    }
}
