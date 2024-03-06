package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Duel extends ICard{
    public static final int copiesCount = 3;

    public Duel(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
