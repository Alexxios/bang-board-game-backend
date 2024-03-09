package cards;

import configurators.ModelsConfiguration;
import models.CardDescription;
import models.cards.playing.*;
import models.cards.weapons.*;
import models.cards.playing.Bang;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("cardMapperBean")
public class CardMapper {
    private final HashMap<PlayingCard, ICard> cards = new HashMap<>();

    public CardMapper(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModelsConfiguration.class);

        // weapons
        cards.put(PlayingCard.Remington, context.getBean("remingtonCardBean", Remington.class));
        cards.put(PlayingCard.Carabine, context.getBean("carabineCardBean", Carabine.class));
        cards.put(PlayingCard.Schofield, context.getBean("schofieldCardBean", Schofield.class));
        cards.put(PlayingCard.Volcanic, context.getBean("volcanicCardBean", Volcanic.class));
        cards.put(PlayingCard.Winchester, context.getBean("winchesterCardBean", Winchester.class));

        // active cards
        cards.put(PlayingCard.Bang, context.getBean("bangCardBean", Bang.class));
        cards.put(PlayingCard.Beer, context.getBean("beerCardBean", Beer.class));
        cards.put(PlayingCard.Lovely, context.getBean("lovelyCardBean", Lovely.class));
        cards.put(PlayingCard.Diligenza, context.getBean("diligenzaCardBean", Diligenza.class));
        cards.put(PlayingCard.Duel, context.getBean("duelCardBean", Duel.class));
        cards.put(PlayingCard.Shop, context.getBean("shopCardBean", Shop.class));
        cards.put(PlayingCard.Gatling, context.getBean("gatlingCardBean", Gatling.class));
        cards.put(PlayingCard.Indians, context.getBean("indiansCardBean", Indians.class));
        cards.put(PlayingCard.Miss, context.getBean("missCardBean", Miss.class));
        cards.put(PlayingCard.Panic, context.getBean("panicCardBean", Panic.class));
        cards.put(PlayingCard.Saloon, context.getBean("saloonCardBean", Saloon.class));
        cards.put(PlayingCard.WellsFargo, context.getBean("wellsFargoCardBean", WellsFargo.class));

        // passive cards
        cards.put(PlayingCard.Barile, context.getBean("barileCardBean", Barile.class));
        cards.put(PlayingCard.Dinamite, context.getBean("dinamiteCardBean", Dinamite.class));
        cards.put(PlayingCard.Aim, context.getBean("aimCardBean", Aim.class));
        cards.put(PlayingCard.Mustang, context.getBean("mustangCardBean", Mustang.class));
        cards.put(PlayingCard.Prison, context.getBean("prisonCardBean", Prison.class));
    }


    public ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard());
    }
    public ICard searchCard(PlayingCard card){
        return cards.get(card);
    }
}
