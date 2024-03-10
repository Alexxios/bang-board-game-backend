package models.callbacks.handlers;

import cards.PlayingCard;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;
import org.springframework.stereotype.Component;

@Component("bangCallbackHandlerBean")
public class BangCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(Event event) {
        return event.getCardDescription().getCard() == PlayingCard.Miss ||
                event.getCardDescription().getCard() == PlayingCard.Beer;
    }

    @Override
    public GameEntity positiveAction(GameEntity game) {
        return game;
    }

    @Override
    public GameEntity negativeAction(GameEntity game) {
        Callback callback = game.getCallbacks().getFirst();
        Event event = callback.getEvent();
        Player victim = game.getPlayer(event.getGetterIndex());
        victim.takeDamage(1);
        game.checkPlayer(event.getGetterIndex());
        return game;
    }
}
