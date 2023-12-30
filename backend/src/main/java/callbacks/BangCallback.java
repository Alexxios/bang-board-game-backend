package callbacks;

import cards.PlayingCards;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.Player;

public class BangCallback implements ICallback {
    @Override
    public boolean checkCallback(Event event) {
        return event.getCardDescription().getCard() == PlayingCards.Miss;
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
