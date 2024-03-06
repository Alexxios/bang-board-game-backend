package models.cards.weapons;

import cards.PlayingCard;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.cards.playing.ICard;

public class Volcanic extends ICard implements IWeapon{
    public final static int copiesCount = 2;

    public Volcanic(){
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

        return new HandleEventResult(true, game);
    }
}
