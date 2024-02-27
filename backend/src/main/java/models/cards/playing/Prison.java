package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Prison extends ICard {

    private static final int copiesCount = 3;

    public Prison(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
