package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

public class Aim extends ICard{
    private static final int copiesCount = 1;

    public Aim(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        game.getPlayer(event.getSenderIndex()).getBuffs().setHasAim(true);
        return new HandleEventResult(true, game);
    }
}
