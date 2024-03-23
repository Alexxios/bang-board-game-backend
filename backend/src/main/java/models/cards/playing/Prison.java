package models.cards.playing;

import cards.Role;
import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("prisonCardBean")
public class Prison extends ICard {

    private static final int copiesCount = 3;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Spades, 11),
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 4),
            new AbstractMap.SimpleEntry<>(Suit.Spades, 10)
    );

    public Prison(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getGetterIndex() == event.getSenderIndex()){
            return new HandleEventResult(false, game);
        }

        Player getter = game.getPlayer(event.getGetterIndex());

        if (getter.getRole() == Role.Sheriff){
            return new HandleEventResult(false, game);
        }

        getter.getBuffs().setHasPrison(true);
        return new HandleEventResult(true, game);
    }
}
