package models.cards.playing;

import callbacks.CallbackType;
import cards.Suit;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("indiansCardBean")
public class Indians extends ICard{

    private static final int copiesCount = 2;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 14),
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 13)
    );


    public Indians(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getGetterIndex() == event.getSenderIndex()){
            return new HandleEventResult(false, game);
        }

        int count = 0, playersCount = game.getPlayers().size(), previousPlayerIndex = event.getSenderIndex();
        for (int i = 0; i < playersCount; ++i){
            if (i != event.getSenderIndex()){
                count++;
                Event newEvent;
                if (count == playersCount - 1){
                    newEvent = new Event(event.getSenderIndex(), i, event.getCardDescription(), event.getCardIndex());
                }else {
                    newEvent = new Event(previousPlayerIndex, i, event.getCardDescription(), event.getCardIndex());
                }
                game.addCallback(new Callback(newEvent, CallbackType.Indians));
                previousPlayerIndex = i;
            }
        }

        game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
