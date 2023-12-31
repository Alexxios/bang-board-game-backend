package models.cards;

import models.Event;
import models.GameEntity;

public class Prison implements ICard {
    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
