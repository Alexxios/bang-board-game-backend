package callbacks.handlers;

import callbacks.handlers.ICallbackHandler;
import cards.PlayingCards;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;

public class BangCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(Event event) {
        return event.getCardDescription().getCard() == PlayingCards.Miss;
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
        Player shooter = game.getPlayer(event.getSenderIndex());
        Player victim = game.getPlayer(event.getGetterIndex());
        victim.takeDamage(shooter.getShootDamage());
        game.checkPlayer(event.getGetterIndex());
        return game;
    }
}
