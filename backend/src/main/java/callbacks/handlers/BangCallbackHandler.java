package callbacks.handlers;

import cards.PlayingCard;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;

public class BangCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(Event event) {
        return event.getCardDescription().getCard() == PlayingCard.Miss;
    }

    @Override
    public GameEntity positiveAction(GameEntity game) {
        game.resetCallback();
        return game;
    }

    @Override
    public GameEntity negativeAction(GameEntity game) {
        Callback callback = game.getCallback();
        Event event = callback.getEvent();
        Player victim = game.getPlayer(event.getGetterIndex());
        victim.takeDamage(1);
        game.checkPlayer(event.getGetterIndex());
        return game;
    }
}
