package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Panic extends ICard{
    private static final int copiesCount = 4;

    public Panic(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
