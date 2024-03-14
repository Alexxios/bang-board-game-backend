package models;

import cards.PlayingCardName;
import cards.Suit;
import lombok.Getter;

@Getter
public class PlayingCard {

    public PlayingCard(){
        this.cardName = PlayingCardName.ClosedCard;
        this.suit = Suit.None;
    }

    public PlayingCard(PlayingCardName cardName, Suit suit, int number){
        this.cardName = cardName;
        this.suit = suit;
        this.number = number;
    }


    private PlayingCardName cardName;
    private Suit suit;
    private int number;
}
