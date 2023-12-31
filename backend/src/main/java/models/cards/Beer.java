package models.cards;

import models.Event;
import models.GameEntity;
import models.Player;

public class Beer implements ICard{

    private static final int healthBoost = 1;

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Player player = game.getPlayer(event.getGetterIndex());
        player.getHealth(healthBoost);
        return game;
    }
}
