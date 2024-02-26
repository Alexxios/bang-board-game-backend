package models.cards;

import models.Event;
import models.GameEntity;

public class Mustang extends ICard{
    private static final int copiesCount = 2;

    public Mustang(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
