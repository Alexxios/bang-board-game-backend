package models.cards.playing;

import cards.Suit;
import characters.Character;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("gatlingCardBean")
public class Gatling extends ICard{
    private static final int copiesCount = 1;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 10)
    );

    public Gatling(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getGetterIndex() == event.getSenderIndex()){
            return new HandleEventResult(false, game);
        }

        for (int i = 0; i < game.getPlayers().size(); ++i){
            if (i != event.getSenderIndex()){
                Player victim = game.getPlayer(i);
                victim.takeDamage(1);

                // Bart Cassidy Ability
                if (victim.getHealth() > 0 && victim.getCharacter() == Character.BartCassidy) {
                    victim.receiveCard(game.drawFirstCard());
                }
            }
        }
        return new HandleEventResult(true, game);
    }
}
