package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.ws.controllers.GameEventsController;

@Service
public class WellsFargo extends ICard{
    private static final int copiesCount = 1;
    private static int cardsCount = 3;

    @Autowired
    private GameEventsController gameEventsController;

    public WellsFargo(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        int playerIndex = event.getSenderIndex();

        for (int i = 0; i < cardsCount; ++i){
            game.getPlayer(playerIndex).getCard(game.getCards().getLast());
            gameEventsController.keepCard(game.getGameId(), game.getCards().getLast());
            game.getCards().removeLast();
        }

        return new HandleEventResult(true, game);
    }
}
