package models.cards.weapons;

import models.Event;
import models.GameEntity;
import models.cards.playing.ICard;

public class Winchester extends ICard implements IWeapon{
    private final static int copiesCount = 1;

    public Winchester(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
