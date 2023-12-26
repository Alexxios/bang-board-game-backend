package server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.services.UsersService;
import java.util.concurrent.ExecutionException;

@RestController
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return usersService.addUser(userId);
    }
}
