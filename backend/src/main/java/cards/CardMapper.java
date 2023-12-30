package cards;

import models.CardDescription;
import models.cards.Bang;
import models.cards.ICard;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<PlayingCards, ICard> cards = new HashMap<>();

    static {
        cards.put(PlayingCards.Bang, new Bang());
    }

    public static ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }

}
