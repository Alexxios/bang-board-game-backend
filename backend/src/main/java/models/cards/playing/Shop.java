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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("shopCardBean")
public class Shop extends ICard {
    private static final int copiesCount = 2;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Clubs, 9),
            new AbstractMap.SimpleEntry<>(Suit.Spades, 12)
    );

    public Shop() {
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        int playersCount = game.getPlayers().size();
        ArrayList<PlayingCard> cards = new ArrayList<>();

        for (int i = 0; i < playersCount; ++i) {
            PlayingCard card = game.getDeck().getLast();
            game.getDiscarded().add(card);
            game.getDeck().removeLast();

            cards.add(card);
        }

        int count = 0, previousPlayerIndex = event.getSenderIndex();
        game.addCallback(new Callback(new Event(previousPlayerIndex, previousPlayerIndex, event.getCardDescription(), event.getCardIndex()), CallbackType.Shop));
        for (int i = 0; i < playersCount; ++i) {
            if (i != event.getSenderIndex()) {
                count++;
                Event newEvent;
                if (count == playersCount - 1) {
                    newEvent = new Event(event.getSenderIndex(), i, event.getCardDescription(), event.getCardIndex());
                } else {
                    newEvent = new Event(previousPlayerIndex, i, event.getCardDescription(), event.getCardIndex());
                }
                game.addCallback(new Callback(newEvent, CallbackType.Shop));
                previousPlayerIndex = i;
            }
        }

        game.setCardsForSelection(cards);
        game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
