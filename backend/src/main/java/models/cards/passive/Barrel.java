package models.cards.passive;

import models.Event;
import models.GameEntity;
import models.cards.Card;

public class Barrel extends Card {
    public Barrel(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return null;
    }
}
