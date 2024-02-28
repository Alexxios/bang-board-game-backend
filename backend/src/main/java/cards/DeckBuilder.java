package cards;

import lombok.Getter;
import models.cards.Card;
import models.cards.active.*;
import models.cards.passive.*;
import models.cards.weapons.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DeckBuilder {
    @Getter
    private static final List<Card> deck = new ArrayList<>();

    // init Barrel
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Queen, Card.Rank.King))
            deck.add(new Barrel(rank, Card.Suit.Spades));
    }

    // init Dynamite
    static {
        deck.add(new Dynamite(Card.Rank.Two, Card.Suit.Hearts));
    }

    // init Jail
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Ten, Card.Rank.Jack))
            deck.add(new Jail(rank, Card.Suit.Spades));

        deck.add(new Jail(Card.Rank.Four, Card.Suit.Hearts));
    }

    // init Mustang
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Eight, Card.Rank.Nine))
            deck.add(new Mustang(rank, Card.Suit.Hearts));
    }

    // init Remington
    static {
        deck.add(new Remington(Card.Rank.King, Card.Suit.Clubs));
    }

    // init Rev. Carabine
    static {
        deck.add(new RevCarabine(Card.Rank.Ace,  Card.Suit.Clubs));
    }

    // init Schofield
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Jack, Card.Rank.Queen))
            deck.add(new Schofield(rank, Card.Suit.Clubs));

        deck.add(new Schofield(Card.Rank.King, Card.Suit.Spades));
    }

    // init Scope
    static {
        deck.add(new Scope(Card.Rank.Ace, Card.Suit.Spades));
    }

    // init Volcanic
    static {
        deck.add(new Volcanic(Card.Rank.Ten, Card.Suit.Spades));
        deck.add(new Volcanic(Card.Rank.Ten, Card.Suit.Clubs));
    }

    // init Winchester
    static {
        deck.add(new Winchester(Card.Rank.Eight, Card.Suit.Spades));
    }

    // init Bang!
    static {
        deck.add(new Bang(Card.Rank.Ace, Card.Suit.Spades));

        for (Card.Rank rank : EnumSet.range(Card.Rank.Two, Card.Rank.Ace))
            deck.add(new Bang(rank, Card.Suit.Diamonds));

        for (Card.Rank rank : EnumSet.range(Card.Rank.Two, Card.Rank.Nine))
            deck.add(new Bang(rank, Card.Suit.Clubs));

        for (Card.Rank rank : EnumSet.range(Card.Rank.Queen, Card.Rank.Ace))
            deck.add(new Bang(rank, Card.Suit.Hearts));
    }

    // init Beer
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Six, Card.Rank.Jack))
            deck.add(new Beer(rank, Card.Suit.Hearts));
    }

    // init Cat Balou
    static {
        deck.add(new CatBalou(Card.Rank.King, Card.Suit.Hearts));

        for (Card.Rank rank : EnumSet.range(Card.Rank.Nine, Card.Rank.Jack))
            deck.add(new CatBalou(rank, Card.Suit.Diamonds));
    }

    // init Duel
    static {
        deck.add(new Duel(Card.Rank.Queen, Card.Suit.Diamonds));
        deck.add(new Duel(Card.Rank.Jack, Card.Suit.Spades));
        deck.add(new Duel(Card.Rank.Eight, Card.Suit.Clubs));
    }

    // init Gatling
    static {
        deck.add(new Gatling(Card.Rank.Ten, Card.Suit.Hearts));
    }

    // init General Store
    static {
        deck.add(new GeneralStore(Card.Rank.Nine, Card.Suit.Clubs));
        deck.add(new GeneralStore(Card.Rank.Queen, Card.Suit.Spades));
    }

    // init Indians!
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.King, Card.Rank.Ace))
            deck.add(new Indians(rank, Card.Suit.Diamonds));
    }

    // init Missed!
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Ten, Card.Rank.Ace))
            deck.add(new Missed(rank, Card.Suit.Clubs));

        for (Card.Rank rank : EnumSet.range(Card.Rank.Two, Card.Rank.Eight))
            deck.add(new Missed(rank, Card.Suit.Spades));
    }

    // init Panic!
    static {
        for (Card.Rank rank : EnumSet.range(Card.Rank.Jack, Card.Rank.Queen))
            deck.add(new Panic(rank, Card.Suit.Hearts));

        deck.add(new Panic(Card.Rank.Ace, Card.Suit.Hearts));
        deck.add(new Panic(Card.Rank.Eight, Card.Suit.Diamonds));
    }

    // init Saloon
    static {
        deck.add(new Saloon(Card.Rank.Five, Card.Suit.Hearts));
    }

    // init Stagecoach
    static {
        deck.add(new Stagecoach(Card.Rank.Nine, Card.Suit.Spades));
        deck.add(new Stagecoach(Card.Rank.Nine, Card.Suit.Spades));
    }

    // init Wells Fargo
    static {
        deck.add(new WellsFargo(Card.Rank.Three, Card.Suit.Hearts));
    }

}
