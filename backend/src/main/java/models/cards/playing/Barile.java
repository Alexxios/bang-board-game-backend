package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("barileCardBean")
public class Barile extends ICard{
    private static final int copiesCount = 2;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Spades, 12),
            new AbstractMap.SimpleEntry<>(Suit.Spades, 13)
    );

    public Barile(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        return new HandleEventResult(false, game);
    }
}
