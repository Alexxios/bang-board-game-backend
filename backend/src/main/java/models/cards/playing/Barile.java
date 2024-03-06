package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;

public class Barile extends ICard{
    private static final int copiesCount = 2;

    public Barile(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        game.getPlayer(event.getSenderIndex()).getBuffs().setHasBarile(true);
        return new HandleEventResult(true, game);
    }
}
