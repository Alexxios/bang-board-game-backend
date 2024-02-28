package models.cards.active;

import models.Event;
import models.GameEntity;
import models.Player;
import models.cards.Card;

public class Beer extends Card {
    private static final int healthBoost = 1;

    public Beer(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Player player = game.getPlayer(event.getGetterIndex());
        player.getHealth(healthBoost);
        return game;
    }
}
