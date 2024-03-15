package models.cards.playing;

import callbacks.CallbackType;
import cards.Suit;
import models.PlayingCard;
import cards.PlayingCardName;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("lovelyCardBean")
public class Lovely extends ICard{
    private static final int copiesCount = 4;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 13),
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 9),
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 10),
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 11)
    );


    public Lovely(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        List<PlayingCard> cards = new ArrayList<>();

        for(int i = 0; i < game.getPlayer(event.getGetterIndex()).getCards().size(); ++i){
            cards.add(new PlayingCard());
        }

        if (cards.isEmpty()){
            return new HandleEventResult(false, game);
        }

        game.setCardsForSelection(cards);

        Event newEvent = new Event(event.getGetterIndex(), event.getSenderIndex(), event.getCardDescription(), event.getCardIndex());
        game.addCallback(new Callback(newEvent, CallbackType.Lovely));
        game.setMotionPlayerIndex(game.getCallbacks().getFirst().getEvent().getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
