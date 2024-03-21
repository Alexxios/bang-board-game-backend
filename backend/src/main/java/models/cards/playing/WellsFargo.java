package models.cards.playing;

import cards.Suit;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
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

    @Autowired
    public WellsFargo(ApplicationContext context){
        super(copiesCount, cardTypesList);
        gameEventsController = context.getBean("gameEventsControllerBean", GameEventsController.class);
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
            gameEventsController.keepCard(game, card);
        }

        return new HandleEventResult(true, game);
    }
}
