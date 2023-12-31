package cards;

import models.CardDescription;
import models.cards.*;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<PlayingCards, ICard> cards = new HashMap<>();

    static {
        cards.put(PlayingCards.Bang, new Bang());
        cards.put(PlayingCards.Prison, new Prison());
        cards.put(PlayingCards.Beer, new Beer());
        cards.put(PlayingCards.Miss, new Miss());
    }

    public static ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }

}
