package server;

import database.FirebaseAuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) throws IOException {
        FirebaseAuth.authorizeFirebase();
        SpringApplication.run(BackendApplication.class, args);
    }
}
