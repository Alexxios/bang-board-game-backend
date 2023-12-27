package models.cards;

import models.Event;
import models.GameEntity;

public interface Card {
    void handlerEvent(GameEntity game, Event event);
}
