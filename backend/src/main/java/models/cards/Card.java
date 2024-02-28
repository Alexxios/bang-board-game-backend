package models.cards;

import lombok.Getter;
import models.Event;
import models.GameEntity;

public abstract class Card {
    public enum Rank {
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King,
        Ace
    }
    public enum Suit {
        Hearts,
        Spades,
        Diamonds,
        Clubs
    }

    @Getter
    protected final Rank rank;
    @Getter
    protected final Suit suit;
    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;

    }
    public abstract GameEntity handlerEvent(GameEntity game, Event event);
}
