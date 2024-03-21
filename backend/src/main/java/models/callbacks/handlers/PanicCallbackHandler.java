package models.callbacks.handlers;

import characters.Character;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import response.models.KeepCard;
import server.ws.controllers.GameEventsController;

@Component("panicCallbackHandlerBean")
public class PanicCallbackHandler implements ICallbackHandler {

    @Autowired
    private GameEventsController gameEventsController;

    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        Event callbackEvent = game.getCallbacks().getFirst().getEvent();
        PlayingCard card = game.getPlayer(callbackEvent.getSenderIndex()).getCards().get(event.getCardIndex());
        int eventSenderIndex = event.getSenderIndex();

        game.getPlayer(eventSenderIndex).getCards().add(card);
        game.getPlayer(callbackEvent.getSenderIndex()).getCards().remove(event.getCardIndex());

        int senderIndex = callbackEvent.getSenderIndex();
        // Susie Lafayette Ability
        if (game.getPlayer(senderIndex).getCharacter() == Character.SuzyLafayette
                && game.getPlayer(senderIndex).getCards().isEmpty()) {
            game.getPlayer(senderIndex).receiveCard(game.drawFirstCard());
        }

        Event newEvent = new Event(eventSenderIndex, event.getGetterIndex(), event.getCardDescription(), event.getCardIndex());
        game.getCallbacks().getFirst().setEvent(newEvent);

        game.getCardsForSelection().clear();
        gameEventsController.keepCard(game, new KeepCard(eventSenderIndex, card));
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
