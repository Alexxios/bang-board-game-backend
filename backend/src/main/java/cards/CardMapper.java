package cards;

import models.CardDescription;
import models.cards.playing.*;
import models.cards.weapons.*;
import models.cards.playing.Bang;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import server.BackendApplication;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.HashMap;

@Service("cardMapperBean")
@Scope("singleton")
public class CardMapper {
    private final HashMap<PlayingCardName, ICard> cards = new HashMap<>();

    public CardMapper(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BackendApplication.class);

        // weapons
        cards.put(PlayingCardName.Remington, context.getBean("remingtonCardBean", Remington.class));
        cards.put(PlayingCardName.Carabine, context.getBean("carabineCardBean", Carabine.class));
        cards.put(PlayingCardName.Schofield, context.getBean("schofieldCardBean", Schofield.class));
        cards.put(PlayingCardName.Volcanic, context.getBean("volcanicCardBean", Volcanic.class));
        cards.put(PlayingCardName.Winchester, context.getBean("winchesterCardBean", Winchester.class));

        // active cards
        cards.put(PlayingCardName.Bang, context.getBean("bangCardBean", Bang.class));
        cards.put(PlayingCardName.Beer, context.getBean("beerCardBean", Beer.class));
        cards.put(PlayingCardName.Lovely, context.getBean("lovelyCardBean", Lovely.class));
        cards.put(PlayingCardName.Diligenza, context.getBean("diligenzaCardBean", Diligenza.class));
        cards.put(PlayingCardName.Duel, context.getBean("duelCardBean", Duel.class));
        cards.put(PlayingCardName.Shop, context.getBean("shopCardBean", Shop.class));
        cards.put(PlayingCardName.Gatling, context.getBean("gatlingCardBean", Gatling.class));
        cards.put(PlayingCardName.Indians, context.getBean("indiansCardBean", Indians.class));
        cards.put(PlayingCardName.Miss, context.getBean("missCardBean", Miss.class));
        cards.put(PlayingCardName.Panic, context.getBean("panicCardBean", Panic.class));
        cards.put(PlayingCardName.Saloon, context.getBean("saloonCardBean", Saloon.class));
        cards.put(PlayingCardName.WellsFargo, context.getBean("wellsFargoCardBean", WellsFargo.class));

        // passive cards
        cards.put(PlayingCardName.Barile, context.getBean("barileCardBean", Barile.class));
        cards.put(PlayingCardName.Dinamite, context.getBean("dinamiteCardBean", Dinamite.class));
        cards.put(PlayingCardName.Aim, context.getBean("aimCardBean", Aim.class));
        cards.put(PlayingCardName.Mustang, context.getBean("mustangCardBean", Mustang.class));
        cards.put(PlayingCardName.Prison, context.getBean("prisonCardBean", Prison.class));
    }


    public ICard searchCard(CardDescription cardDescription){
        return cards.get(cardDescription.getCard().getCardName());
    }
    public ICard searchCard(PlayingCardName card){
        return cards.get(card);
    }
}
