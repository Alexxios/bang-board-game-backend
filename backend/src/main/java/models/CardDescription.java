package models;

import lombok.Getter;
import models.cards.Card;

@Getter
public class CardDescription {
    private final Class<? extends Card> cardClass;
    private final Card.Rank rank;
    private final Card.Suit suit;

    public CardDescription(Class<? extends Card> cardClass, Card.Rank rank, Card.Suit suit) {
        this.cardClass = cardClass;
        this.rank = rank;
        this.suit = suit;
    }
}
