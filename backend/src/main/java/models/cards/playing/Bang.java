package models.cards.playing;

import callbacks.CallbackType;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import response.models.NextMotionResult;
import server.ws.controllers.GameEventsController;

import java.sql.SQLOutput;

@Component("bangCardBean")
public class Bang extends ICard {

    private static final int copiesCount = 25;

    public Bang(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {

        Callback callback = new Callback(event, CallbackType.Bang);
        game.setCallback(callback);
        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
