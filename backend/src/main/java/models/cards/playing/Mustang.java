package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Mustang extends ICard{
    private static final int copiesCount = 2;

    public Mustang(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
