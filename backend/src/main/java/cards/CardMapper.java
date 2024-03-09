package cards;

import models.CardDescription;
import models.cards.playing.*;
import models.cards.weapons.*;
import models.cards.playing.Bang;

import java.util.HashMap;

public class CardMapper {
    private static final HashMap<PlayingCard, ICard> cards = new HashMap<>();

    static {
        // weapons
        cards.put(PlayingCard.Remington, new Remington());
        cards.put(PlayingCard.Carabine, new Carabine());
        cards.put(PlayingCard.Schofield, new Schofield());
        cards.put(PlayingCard.Volcanic, new Volcanic());
        cards.put(PlayingCard.Winchester, new Winchester());

        // active cards
        cards.put(PlayingCard.Bang, new Bang());
        cards.put(PlayingCard.Beer, new Beer());
        cards.put(PlayingCard.Lovely, new Lovely());
        cards.put(PlayingCard.Diligenza, new Diligenza());
        cards.put(PlayingCard.Duel, new Duel());
        cards.put(PlayingCard.Shop, new Shop());
        cards.put(PlayingCard.Gatling, new Gatling());
        cards.put(PlayingCard.Indians, new Indians());
        cards.put(PlayingCard.Miss, new Miss());
        cards.put(PlayingCard.Panic, new Panic());
        cards.put(PlayingCard.Saloon, new Saloon());
        cards.put(PlayingCard.WellsFargo, new WellsFargo());

        // passive cards
        cards.put(PlayingCard.Barile, new Barile());
        cards.put(PlayingCard.Dinamite, new Dinamite());
        cards.put(PlayingCard.Aim, new Aim());
        cards.put(PlayingCard.Mustang, new Mustang());
        cards.put(PlayingCard.Prison, new Prison());
    }

    public static ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }
    public static ICard searchCard(PlayingCard card){
        return cards.get(card);
    }
}
