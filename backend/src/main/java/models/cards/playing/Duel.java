package models.cards.playing;

import callbacks.CallbackType;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

@Component("duelCardBean")
public class Duel extends ICard{
    public static final int copiesCount = 3;

    public Duel(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getGetterIndex() == event.getSenderIndex()){
            return new HandleEventResult(false, game);
        }


        Callback callback = new Callback(event, CallbackType.Duel);
        game.getCallbacks().add(callback);
        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
