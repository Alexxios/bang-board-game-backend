package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Aim extends ICard{
    private static final int copiesCount = 1;

    public Aim(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
