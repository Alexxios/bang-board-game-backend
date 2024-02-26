package models.cards;

import models.Event;
import models.GameEntity;

public class Lovely extends ICard{
    private static final int copiesCount = 4;

    public Lovely(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
