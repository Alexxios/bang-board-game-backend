package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("aimCardBean")
public class Aim extends ICard{
    private static final int copiesCount = 1;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Spades, 14)
    );

    public Aim(){
        super(copiesCount, cardTypesList);
    }


    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        game.getPlayer(event.getSenderIndex()).getBuffs().setHasAim(true);
        return new HandleEventResult(true, game);
    }
}
