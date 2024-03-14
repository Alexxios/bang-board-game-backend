package models.cards.playing;

import callbacks.CallbackType;
import cards.PlayingCard;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lovelyCardBean")
public class Lovely extends ICard{
    private static final int copiesCount = 4;

    public Lovely(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        List<PlayingCard> cards = game.getPlayer(event.getGetterIndex()).getCards();

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
