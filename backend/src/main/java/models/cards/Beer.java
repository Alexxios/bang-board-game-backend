package models.cards;

import models.Event;
import models.GameEntity;
import models.Player;
import org.springframework.expression.spel.ast.BeanReference;

public class Beer extends ICard{

    private static final int healthBoost = 1;
    private static final int copiesCount = 6;

    public Beer(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Player player = game.getPlayer(event.getGetterIndex());
        player.getHealth(healthBoost);
        return game;
    }
}
