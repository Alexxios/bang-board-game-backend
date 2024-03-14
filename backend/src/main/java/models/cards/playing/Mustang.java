package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("mustangCardBean")
public class Mustang extends ICard{
    private static final int copiesCount = 2;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 8),
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 9)
    );


    public Mustang(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        game.getPlayer(event.getSenderIndex()).getBuffs().setHasMustang(true);
        return new HandleEventResult(true, game);
    }
}
