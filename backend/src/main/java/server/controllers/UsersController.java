package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/add-user")
    public String addUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        if (usersService.isUserExists(userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return usersService.addUser(userId);
    }
}
