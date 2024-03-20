package models.callbacks.handlers;

import characters.Character;
import models.Event;
import models.GameEntity;
import org.springframework.stereotype.Component;

@Component("lovelyCallbackHandlerBean")
public class LovelyCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        Event callbackEvent = game.getCallbacks().getFirst().getEvent();
        game.getPlayer(callbackEvent.getSenderIndex()).getCards().remove(event.getCardIndex());

        int senderIndex = callbackEvent.getSenderIndex();
        // Susie Lafayette Ability
        if (game.getPlayer(senderIndex).getCharacter() == Character.SusieLafayette
                && game.getPlayer(senderIndex).getCards().isEmpty()) {
            game.getPlayer(senderIndex).receiveCard(game.drawFirstCard());
        }

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
