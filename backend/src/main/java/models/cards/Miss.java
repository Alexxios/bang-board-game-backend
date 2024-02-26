package models.cards;

import models.Event;
import models.GameEntity;

public class Miss extends ICard {
    private static final int copiesCount = 12;

    public Miss(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
