package callbacks.handlers;

import models.Event;
import models.GameEntity;

public interface ICallbackHandler {
    boolean checkCallback(Event event);
    GameEntity positiveAction(GameEntity game);
    GameEntity negativeAction(GameEntity game);
}
