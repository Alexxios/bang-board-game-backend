package models.callbacks.handlers;

import characters.Character;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import org.springframework.stereotype.Component;

@Component("panicCallbackHandlerBean")
public class PanicCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {


        Event callbackEvent = game.getCallbacks().getFirst().getEvent();
        PlayingCard card = game.getPlayer(callbackEvent.getSenderIndex()).getCards().get(event.getCardIndex());
        game.getPlayer(event.getSenderIndex()).getCards().add(card);
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
