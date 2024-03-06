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
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        game.getPlayer(event.getSenderIndex()).getBuffs().setHasMustang(true);
        return new HandleEventResult(true, game);
    }
}
