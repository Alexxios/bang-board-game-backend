package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

@Component("gatlingCardBean")
public class Gatling extends ICard{
    private static final int copiesCount = 1;

    public Gatling(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        for (int i = 0; i < game.getPlayers().size(); ++i){
            if (i != event.getSenderIndex()){
                game.getPlayer(i).takeDamage(1);
            }
        }
        return new HandleEventResult(true, game);
    }
}
