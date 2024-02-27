package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Barile extends ICard{
    private static final int copiesCount = 2;

    public Barile(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
