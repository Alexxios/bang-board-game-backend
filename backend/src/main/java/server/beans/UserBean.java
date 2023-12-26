package server.beans;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@SessionScope
public class UserBean {
    private String sessionId;
}
