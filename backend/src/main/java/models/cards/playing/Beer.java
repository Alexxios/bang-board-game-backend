package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

@Component("beerCardBean")
public class Beer extends ICard{

    private static final int healthBoost = 1;
    private static final int copiesCount = 6;

    public Beer(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }
        Player player = game.getPlayer(event.getGetterIndex());
        int previousHealth = player.getHealth();
        player.updateHealth(healthBoost);
        if (player.getHealth() == previousHealth){
            return new HandleEventResult(false, game);
        }
        return new HandleEventResult(true, game);
    }
}
