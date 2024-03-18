package server;

import database.FirebaseAuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan({"database", "callbacks", "models", "helpers", "cards"})
public class BackendApplication {
    public static void main(String[] args) throws IOException {
        FirebaseAuth firebaseAuth = new FirebaseAuth();
        firebaseAuth.authorizeFirebase();
        SpringApplication.run(BackendApplication.class, args);
    }
}
