package models.cards.playing;

import cards.Suit;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import response.models.KeepCard;
import server.ws.controllers.GameEventsController;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("diligenzaCardBean")
public class Diligenza extends ICard{
    private static final int copiesCount = 2;
    private static int cardsCount = 2;

    @Autowired
    private GameEventsController gameEventsController;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Spades, 9),
            new AbstractMap.SimpleEntry<>(Suit.Spades, 9)
    );

    public Diligenza(){
        super(copiesCount, cardTypesList);
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
            gameEventsController.keepCard(game, new KeepCard(playerIndex, card));
        }

        return new HandleEventResult(true, game);
    }
}
