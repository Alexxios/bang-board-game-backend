package configurators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import server.ws.controllers.GameEventsController;

@Configuration
@ComponentScan("server.ws.controllers")
public class GameEventsConfiguration {
}
