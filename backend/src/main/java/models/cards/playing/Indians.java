package models.cards.playing;

import callbacks.CallbackType;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

@Component("indiansCardBean")
public class Indians extends ICard{

    private static final int copiesCount = 2;

    public Indians(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        int count = 0, playersCount = game.getPlayers().size(), previousPlayerIndex = event.getSenderIndex();
        for (int i = 0; i < playersCount; ++i){
            if (i != event.getSenderIndex()){
                count++;
                Event newEvent;
                if (count == playersCount - 1){
                    newEvent = new Event(event.getSenderIndex(), i, event.getCardDescription(), event.getCardIndex());
                }else {
                    newEvent = new Event(previousPlayerIndex, i, event.getCardDescription(), event.getCardIndex());
                }
                game.addCallback(new Callback(newEvent, CallbackType.Indians));
                previousPlayerIndex = i;
            }
        }

        game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
