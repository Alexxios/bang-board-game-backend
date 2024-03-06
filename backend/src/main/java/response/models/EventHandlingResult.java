package response.models;

import models.Event;
import models.GameEntity;

public record EventHandlingResult(boolean isSuccess, Event event, GameEntity game) {
}
