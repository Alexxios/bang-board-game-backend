package cards;

import models.CardDescription;
import models.cards.Card;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<CardDescription, Card> cards = new HashMap<>();

    static {
        for (Card card : DeckBuilder.getDeck()) {
            CardDescription description = new CardDescription(card.getClass(), card.getRank(), card.getSuit());
            cards.put(description, card);
        }
    }

    public static Card searchCard(CardDescription cardDescription){
        return cards.get(cardDescription);
    }
    public static Card searchCard(Class<? extends Card> cardClass, Card.Rank rank, Card.Suit suit){
        return cards.get(new CardDescription(cardClass, rank, suit));
    }
}
