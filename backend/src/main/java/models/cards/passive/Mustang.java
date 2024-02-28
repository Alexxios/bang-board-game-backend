package models.cards.passive;

import models.Event;
import models.GameEntity;
import models.cards.Card;

public class Mustang extends Card {

    public Mustang(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
