package models.cards.weapons;

import models.Event;
import models.GameEntity;
import models.cards.playing.ICard;

public class Schofield extends ICard implements IWeapon{
    public final static int copiesCount = 3;

    public Schofield(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
