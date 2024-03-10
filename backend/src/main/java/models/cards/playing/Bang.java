package models.cards.playing;

import callbacks.CallbackType;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import response.models.NextMotionResult;
import server.BackendApplication;
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
        int sender = event.getSenderIndex(), getter = event.getGetterIndex(), playersCount = game.getPlayers().size();
        int weaponDistance = game.getPlayer(sender).getShootingDistance();
        int distance = Math.min(Math.abs(sender - getter), Math.abs(playersCount + sender - getter));

        if (game.getPlayer(sender).getBuffs().isHasAim()){
            distance--;
        }

        if (game.getPlayer(getter).getBuffs().isHasMustang()){
            distance++;
        }

        if (distance > weaponDistance){
            return new HandleEventResult(false, game);
        }

        Callback callback = new Callback(event, CallbackType.Bang);
        game.getCallbacks().add(callback);
        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
