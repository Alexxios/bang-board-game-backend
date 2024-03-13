package models.cards.playing;

import cards.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.stereotype.Component;
import server.ws.controllers.GameEventsController;

@Component("diligenzaCardBean")
public class Diligenza extends ICard{
    private static final int copiesCount = 2;
    private static int cardsCount = 2;

    private GameEventsController gameEventsController;

    public Diligenza(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        int playerIndex = event.getSenderIndex();

        for (int i = 0; i < cardsCount; ++i){
            PlayingCard card = game.drawFirstCard();
            game.getPlayer(playerIndex).receiveCard(card);
            gameEventsController.keepCard(game.getGameId(), card);
        }

        return new HandleEventResult(true, game);
    }
}
