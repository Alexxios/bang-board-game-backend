package models.cards.playing;

import cards.Suit;
import lombok.Getter;
import models.Event;
import models.GameEntity;
import models.HandleEventResult;
import models.enums.Suits;

import java.awt.color.ICC_ColorSpace;
import java.util.List;
import java.util.Map;

public abstract class ICard {

    public ICard(int copiesCount, List<Map.Entry<Suit, Integer>> cardsTypesList) {
        this.copiesCount = copiesCount;
        this.cardsTypesList = cardsTypesList;
    }

    protected int copiesCount;
    @Getter
    List<Map.Entry<Suit, Integer>> cardsTypesList;

    public int getCardCopies(){
        return copiesCount;
    }

    public abstract HandleEventResult handlerEvent(GameEntity game, Event event);
}
