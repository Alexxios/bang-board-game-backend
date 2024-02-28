package models.cards.active;

import models.Event;
import models.GameEntity;
import models.cards.Card;

public class Saloon extends Card {

    public Saloon(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
