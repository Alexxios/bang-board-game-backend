package models.cards.playing;

import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import server.BackendApplication;
import server.ws.controllers.GameEventsController;

@Component("wellsFargoCardBean")
public class WellsFargo extends ICard{
    private static final int copiesCount = 1;
    private static int cardsCount = 3;

    private GameEventsController gameEventsController;

    public WellsFargo(){
        super(copiesCount);
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext(BackendApplication.class);
        gameEventsController = parentContext.getBean("gameEventsControllerBean", GameEventsController.class);
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
