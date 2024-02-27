package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Aim extends ICard{
    private static final int copiesCount = 1;

    public Aim(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
