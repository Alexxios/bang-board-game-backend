package models.callbacks.handlers;

import cards.PlayingCardName;
import models.PlayingCard;
import characters.Character;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;
import org.springframework.stereotype.Component;

@Component("indiansCallbackHandlerBean")
public class IndiansCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        PlayingCard card = event.getCardDescription().getCard();
        boolean result = card.getCardName() == PlayingCardName.Bang;
        if (game.getPlayer(event.getSenderIndex()).getCharacter() == Character.CalamityJanet){
            result |= card.getCardName() == PlayingCardName.Miss;
        }
        return result;
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

        // Bart Cassidy Ability
        if (victim.getHealth() > 0 && victim.getCharacter() == Character.BartCassidy) {
            victim.receiveCard(game.drawFirstCard());
        }

        return game;
    }
}
