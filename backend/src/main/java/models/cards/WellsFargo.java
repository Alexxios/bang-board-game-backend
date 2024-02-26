package models.cards;

import models.Event;
import models.GameEntity;

public class WellsFargo extends ICard{
    private static final int copiesCount = 1;

    public WellsFargo(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
