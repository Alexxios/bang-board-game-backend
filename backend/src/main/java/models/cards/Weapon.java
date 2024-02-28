package models.cards;

import models.Event;
import models.GameEntity;

public abstract class Weapon extends Card {
    protected static int distance;

    public Weapon(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
