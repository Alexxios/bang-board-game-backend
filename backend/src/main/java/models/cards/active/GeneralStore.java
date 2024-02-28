package models.cards.active;

import models.Event;
import models.GameEntity;
import models.cards.Card;

public class GeneralStore extends Card {

    public GeneralStore(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        return game;
    }
}
