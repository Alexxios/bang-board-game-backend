package callbacks;

import models.Callback;
import models.Event;
import models.GameEntity;

public interface ICallback {
    boolean checkCallback(Event event);
    GameEntity positiveAction(GameEntity game);
    GameEntity negativeAction(GameEntity game);
}
