package models.cards;

import callbacks.CallbackType;
import models.Callback;
import models.Event;
import models.GameEntity;

public class Bang implements ICard {

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Callback callback = new Callback(event.getGetterIndex(), event.getSenderIndex(), CallbackType.Bang);
        game.setCallback(callback);
        return game;
    }


}
