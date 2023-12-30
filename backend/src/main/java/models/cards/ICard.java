package models.cards;

import models.Event;
import models.GameEntity;

public interface ICard {
    GameEntity handlerEvent(GameEntity game, Event event);
}
