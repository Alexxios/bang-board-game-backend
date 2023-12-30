package models.cards;

import models.Event;
import models.GameEntity;

public interface Card {
    GameEntity handlerEvent(GameEntity game, Event event);
}
