package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

@Component("missCardBean")
public class Miss extends ICard {
    private static final int copiesCount = 12;

    public Miss(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(true, game);
    }
}
