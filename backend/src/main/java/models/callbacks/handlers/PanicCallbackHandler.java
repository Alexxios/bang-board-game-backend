package models.callbacks.handlers;

import models.PlayingCard;
import models.Callback;
import models.Event;
import models.GameEntity;
import org.springframework.stereotype.Component;

@Component("panicCallbackHandlerBean")
public class PanicCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        PlayingCard card = event.getCardDescription().getCard();
        game.getPlayer(event.getSenderIndex()).getCards().add(card);
        Event callbackEvent = game.getCallbacks().getFirst().getEvent();
        game.getPlayer(callbackEvent.getSenderIndex()).getCards().remove(event.getCardIndex());
        Event newEvent = new Event(event.getSenderIndex(), event.getGetterIndex(), event.getCardDescription(), event.getCardIndex());
        game.getCallbacks().getFirst().setEvent(newEvent);
        game.getCardsForSelection().clear();
        return true;
    }

    @Override
    public GameEntity positiveAction(GameEntity game) {
        return game;
    }

    @Override
    public GameEntity negativeAction(GameEntity game) {
        return game;
    }
}
