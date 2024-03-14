package models.cards.playing;

import cards.Suit;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import server.BackendApplication;
import server.ws.controllers.GameEventsController;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("wellsFargoCardBean")
public class WellsFargo extends ICard{
    private static int cardsCount = 3;
    private static final int copiesCount = 1;
    private GameEventsController gameEventsController;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 3)
    );

    public WellsFargo(){
        super(copiesCount, cardTypesList);
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
            PlayingCard card = game.drawFirstCard();
            game.getPlayer(playerIndex).receiveCard(card);
            gameEventsController.keepCard(game.getGameId(), card);
        }

        return new HandleEventResult(true, game);
    }
}
