package cards;

import models.CardDescription;
import models.cards.Bang;
import models.cards.Card;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<PlayingCards, Card> cards = new HashMap<>();

    static {
        cards.put(PlayingCards.Bang, new Bang());
    }

    public static Card searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }

}
