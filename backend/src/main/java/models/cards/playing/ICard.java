package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.enums.Suits;

import java.awt.color.ICC_ColorSpace;

public abstract class ICard {
    public ICard(int copiesCount){
        this.copiesCount = copiesCount;
    }
    protected int copiesCount;
    protected int cardNumber;
    protected Suits suit;
    public abstract GameEntity handlerEvent(GameEntity game, Event event);
}
