package models.cards.playing;

import callbacks.CallbackType;
import models.Callback;
import models.Event;
import models.GameEntity;

public class Bang extends ICard {

    private static final int copiesCount = 25;

    public Bang(){
        super(copiesCount);
    }

    @Override
    public GameEntity handlerEvent(GameEntity game, Event event) {
        Callback callback = new Callback(event, CallbackType.Bang);
        game.setCallback(callback);
        game.setMotionPlayerIndex(event.getGetterIndex());
        return game;
    }


}
