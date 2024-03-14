package models.cards.playing;

import callbacks.CallbackType;
import cards.Suit;
import models.PlayingCard;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("panicCardBean")
public class Panic extends ICard{
    private static final int copiesCount = 4;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 11),
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 12),
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 14),
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 8)
    );

    public Panic(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        List<PlayingCard> cards = game.getPlayer(event.getGetterIndex()).getCards();

        if (cards.isEmpty()){
            return new HandleEventResult(false, game);
        }

        game.setCardsForSelection(cards);

        Event newEvent = new Event(event.getGetterIndex(), event.getSenderIndex(), event.getCardDescription(), event.getCardIndex());
        game.addCallback(new Callback(newEvent, CallbackType.Panic));
        game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
