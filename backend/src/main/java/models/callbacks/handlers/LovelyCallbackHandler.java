package models.callbacks.handlers;

import cards.PlayingCard;
import models.Event;
import models.GameEntity;
import org.springframework.stereotype.Component;

@Component("lovelyCallbackHandlerBean")
public class LovelyCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
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
