package models.cards.weapons;

import cards.Suit;
import models.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.cards.playing.ICard;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("winchesterCardBean")
public class Winchester extends ICard implements IWeapon{
    private final static int copiesCount = 1;
    private final static int shootingDistance = 5;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Spades, 8)
    );

    public Winchester(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }

        int playerIndex = event.getSenderIndex();
        PlayingCard card = event.getCardDescription().getCard();
        game.getPlayers().get(playerIndex).setWeapon(card);
        game.getPlayers().get(playerIndex).setShootingDistance(shootingDistance);

        return new HandleEventResult(true, game);
    }
}
