package models.callbacks.handlers;

import cards.PlayingCard;
import models.Event;
import models.GameEntity;
import org.springframework.stereotype.Component;

@Component("lovelyCallbackHandlerBean")
public class LovelyCallbackHandler implements ICallbackHandler {
    @Override
    public boolean checkCallback(GameEntity game, Event event) {
        return event.getCardDescription().getCard() == PlayingCard.Miss;
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
