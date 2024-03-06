package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Dinamite extends ICard{
    private static final int copiesCount = 1;

    public Dinamite(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
