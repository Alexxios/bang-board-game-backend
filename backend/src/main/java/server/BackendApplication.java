package server;

import database.FirebaseAuth;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.ws.controllers.GameEventsController;
import server.ws.controllers.WebSocketConfig;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class BackendApplication {



    public static void main(String[] args) throws IOException {
        FirebaseAuth.authorizeFirebase();
        SpringApplication.run(BackendApplication.class, args);

    }
}
