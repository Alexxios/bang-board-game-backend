package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("missCardBean")
public class Miss extends ICard {
    private static final int copiesCount = 12;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = new ArrayList<>();

    {
        for (int i = 10; i <= 14; ++i){
            cardsTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Clubs, i));
        }
        for (int i = 2; i <= 8; ++i){
            cardsTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Spades, i));
        }
    }

    public Miss(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (game.getCallbacks().isEmpty()){
            return new HandleEventResult(false, game);
        }
        return new HandleEventResult(true, game);
    }
}
