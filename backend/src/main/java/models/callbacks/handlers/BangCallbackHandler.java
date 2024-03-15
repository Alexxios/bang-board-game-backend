package models.callbacks.handlers;

import cards.PlayingCardName;
import cards.Suit;
import models.PlayingCard;
import characters.Character;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;
import org.springframework.stereotype.Component;

@Component("bangCallbackHandlerBean")
public class BangCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        PlayingCard card =  event.getCardDescription().getCard();
        boolean result =  card.getCardName() == PlayingCardName.Miss || card.getCardName() == PlayingCardName.Beer;
        if (game.getPlayer(event.getSenderIndex()).getCharacter() == Character.PoorJane){
            result |= card.getCardName() == PlayingCardName.Bang;
        }
        System.out.println(card.getCardName());
        if (card.getCardName() == PlayingCardName.Barile){
            if (game.getDeck().getLast().getSuit() == Suit.Hearts){
                int index = event.getCardIndex();
                game.getPlayer(event.getSenderIndex()).getCards().add(index, card);
                PlayingCard cardToDelete = game.getDeck().getFirst();
                game.getDiscarded().add(cardToDelete);
                game.getDeck().removeLast();
                return true;
            }
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
        game.checkPlayer(event.getGetterIndex());
        return game;
    }
}
