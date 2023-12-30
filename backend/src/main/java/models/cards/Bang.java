package models.cards;

import models.Event;
import models.GameEntity;
import models.Player;

public class Bang implements Card{
    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Player shooter = game.getPlayer(event.getSenderIndex());
        Player victim = game.getPlayer(event.getGetterIndex());
        victim.takeDamage(shooter.getShootDamage());
        game.checkPlayer(event.getGetterIndex());
        return game;
    }
}
