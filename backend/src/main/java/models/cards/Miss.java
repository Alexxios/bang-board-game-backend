package models.cards;

import models.Event;
import models.GameEntity;

public class Miss implements ICard {
    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
