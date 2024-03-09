package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

@Component("prisonCardBean")
public class Prison extends ICard {

    private static final int copiesCount = 3;

    public Prison(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
