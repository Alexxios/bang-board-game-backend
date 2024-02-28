package models.cards.active;

import models.Event;
import models.GameEntity;
import models.cards.Card;

public class CatBalou extends Card {

    public CatBalou(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
