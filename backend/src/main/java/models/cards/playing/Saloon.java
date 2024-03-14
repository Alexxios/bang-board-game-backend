package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("saloonCardBean")
public class Saloon extends ICard{
    private static final int copiesCount = 1;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Hearts, 5)
    );

    public Saloon(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        for (Player player : game.getPlayers()){
            player.updateHealth(1);
        }
        return new HandleEventResult(true, game);
    }
}
