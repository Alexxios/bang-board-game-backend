package helpers;
import cards.CardMapper;
import cards.PlayingCardName;
import cards.Suit;
import models.PlayingCard;
import models.cards.playing.ICard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardsGenerator {

    @Autowired
    private ApplicationContext context;

    public List<PlayingCard> generateCards(){

        CardMapper cardMapper = context.getBean("cardMapperBean", CardMapper.class);

        ArrayList<PlayingCard> result = new ArrayList<>();
        for (PlayingCardName cardType : PlayingCardName.values()){
            if (cardType.equals(PlayingCardName.ClosedCard)){
                continue;
            }
            ICard card = cardMapper.searchCard(cardType);
            for (Map.Entry<Suit, Integer> cardStats : card.getCardsTypesList()){
                result.add(new PlayingCard(cardType, cardStats.getKey(), cardStats.getValue()));
            }
        }
        Collections.shuffle(result);
        return result;
    }
}
