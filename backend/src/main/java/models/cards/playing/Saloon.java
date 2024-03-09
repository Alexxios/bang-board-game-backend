package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

@Component("saloonCardBean")
public class Saloon extends ICard{
    private static final int copiesCount = 1;

    public Saloon(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        for (Player player : game.getPlayers()){
            player.updateHealth(1);
        }
        return new HandleEventResult(true, game);
    }
}
