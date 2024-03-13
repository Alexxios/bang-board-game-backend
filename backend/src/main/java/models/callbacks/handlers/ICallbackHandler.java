package models.callbacks.handlers;

import models.Event;
import models.GameEntity;

public interface ICallbackHandler {
    boolean checkCallback(GameEntity game, Event event);
    GameEntity positiveAction(GameEntity game);
    GameEntity negativeAction(GameEntity game);
}
