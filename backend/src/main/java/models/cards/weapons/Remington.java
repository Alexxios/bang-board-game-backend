package models.cards.weapons;

import cards.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.cards.playing.ICard;

public class Remington extends ICard implements IWeapon{
    private final static int copiesCount = 1;
    private final static int shootingDistance = 3;

    public Remington(){
        super(copiesCount);
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
