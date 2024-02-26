package models.cards;

import models.Event;
import models.GameEntity;

public class Gatling extends ICard{
    private static final int copiesCount = 1;

    public Gatling(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
