package models.cards.active;

import callbacks.CallbackType;
import models.Callback;
import models.Event;
import models.GameEntity;
import models.cards.Card;

public class Bang extends Card {

    public Bang(Rank rank, Suit suit) {
        super(rank, suit);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Callback callback = new Callback(event, CallbackType.Bang);
        game.setCallback(callback);
        game.setMotionPlayerIndex(event.getGetterIndex());
        return game;
    }


}
