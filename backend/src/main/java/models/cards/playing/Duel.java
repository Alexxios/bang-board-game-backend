package models.cards.playing;

import models.Event;
import models.GameEntity;

public class Duel extends ICard{
    public static final int copiesCount = 3;

    public Duel(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
