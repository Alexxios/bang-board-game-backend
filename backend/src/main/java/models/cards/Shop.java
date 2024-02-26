package models.cards;

import models.Event;
import models.GameEntity;

public class Shop extends ICard{
    private static final int copiesCount = 2;

    public Shop(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
