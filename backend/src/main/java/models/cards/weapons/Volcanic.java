package models.cards.weapons;

import models.Event;
import models.GameEntity;
import models.cards.playing.ICard;

public class Volcanic extends ICard implements IWeapon{
    public final static int copiesCount = 2;

    public Volcanic(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
