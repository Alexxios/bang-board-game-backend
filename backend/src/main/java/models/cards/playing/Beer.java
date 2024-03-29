package models.cards.playing;

import cards.Suit;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.Player;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("beerCardBean")
public class Beer extends ICard{

    private static final int healthBoost = 1;
    private static final int copiesCount = 6;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = new ArrayList<>();

    static {
        for (int i = 6; i <= 11; ++i){
            cardTypesList.add( new AbstractMap.SimpleEntry<>(Suit.Hearts, i));
        }
    }

    public Beer(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getSenderIndex() != event.getGetterIndex()){
            return new HandleEventResult(false, game);
        }
        Player player = game.getPlayer(event.getGetterIndex());
        int previousHealth = player.getHealth();
        player.updateHealth(healthBoost);
        if (player.getHealth() == previousHealth){
            return new HandleEventResult(false, game);
        }
        return new HandleEventResult(true, game);
    }
}
