package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Indians extends ICard{

    private static final int copiesCount = 2;

    public Indians(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
