package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Saloon extends ICard{
    private static final int copiesCount = 1;

    public Saloon(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
