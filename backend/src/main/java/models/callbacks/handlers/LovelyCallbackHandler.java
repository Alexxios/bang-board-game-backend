package models.callbacks.handlers;

import cards.PlayingCardName;
import characters.Character;
import models.Event;
import models.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import response.models.KeepCard;
import response.models.OnCardPlay;
import server.ws.controllers.GameEventsController;

@Component("lovelyCallbackHandlerBean")
public class LovelyCallbackHandler implements ICallbackHandler {
    @Autowired
    private GameEventsController gameEventsController;

    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        Event callbackEvent = game.getCallbacks().getFirst().getEvent();
        int senderIndex = callbackEvent.getSenderIndex(),
            getterIndex = callbackEvent.getGetterIndex(),
            cardIndex = callbackEvent.getCardIndex();
        PlayingCardName cardName = game.getPlayer(senderIndex).getCards().get(cardIndex).getCardName();

        game.getPlayer(senderIndex).getCards().remove(event.getCardIndex());


        // Susie Lafayette Ability
        if (game.getPlayer(senderIndex).getCharacter() == Character.SuzyLafayette
                && game.getPlayer(senderIndex).getCards().isEmpty()) {
            game.getPlayer(senderIndex).receiveCard(game.drawFirstCard());
        }

        Event newEvent = new Event(event.getSenderIndex(), event.getGetterIndex(), event.getCardDescription(), event.getCardIndex());
        game.getCallbacks().getFirst().setEvent(newEvent);

        game.getCardsForSelection().clear();
        gameEventsController.cardPlay(game, new OnCardPlay(getterIndex, senderIndex, cardName,  event.getCardIndex()));
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
