package models.callbacks.handlers;

import cards.PlayingCard;
import characters.Character;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;
import org.springframework.stereotype.Component;

@Component("shopCallbackHandlerBean")
public class ShopCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        PlayingCard card = event.getCardDescription().getCard();
        System.out.println( game.getPlayer(event.getSenderIndex()).getCards().size());
        game.getPlayer(event.getSenderIndex()).getCards().add(card);
        game.getCardsForSelection().remove(event.getCardIndex());
        System.out.println( game.getPlayer(event.getSenderIndex()).getCards().size());
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
