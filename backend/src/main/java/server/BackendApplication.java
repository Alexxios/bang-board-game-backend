package server;

import database.FirebaseAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan({"database", "callbacks", "helpers", "cards", "server", "models"})
public class BackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

    public static void main(String[] args) throws IOException {
        FirebaseAuth firebaseAuth = new FirebaseAuth();
        firebaseAuth.authorizeFirebase();
        logger.info("Starting server...");
        SpringApplication.run(BackendApplication.class, args);
    }
}
