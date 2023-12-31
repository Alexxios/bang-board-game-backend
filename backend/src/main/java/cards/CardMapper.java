package cards;

import models.CardDescription;
import models.cards.*;
import server.BackendApplication;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<PlayingCard, ICard> cards = new HashMap<>();

    static {
        cards.put(PlayingCard.Bang, new Bang());
        cards.put(PlayingCard.Prison, new Prison());
        cards.put(PlayingCard.Beer, new Beer());
        cards.put(PlayingCard.Miss, new Miss());
    }

    public static ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }
    public static ICard searchCard(PlayingCard card){
        return cards.get(card);
    }
}
