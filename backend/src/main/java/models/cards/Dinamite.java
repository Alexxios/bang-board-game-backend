package models.cards;

import models.Event;
import models.GameEntity;

public class Dinamite extends ICard{
    private static final int copiesCount = 1;

    public Dinamite(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
