package helpers;
import cards.CardMapper;
import cards.PlayingCard;
import models.cards.playing.ICard;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

public class CardsGenerator {

    private static final int maxRepeat = 5;

    public static List<PlayingCard> generateCards(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CardMapper.class);

        CardMapper cardMapper = context.getBean("cardMapperBean", CardMapper.class);

        ArrayList<PlayingCard> result = new ArrayList<>();
        Random random = new Random();
        for (PlayingCard cardType : PlayingCard.values()){
            ICard card = cardMapper.searchCard(cardType);
            if (card == null){
                continue;
            }
            for (int i = 0; i < card.getCardCopies(); ++i){
                result.add(cardType);
            }
        }
        Collections.shuffle(result);
        return result;
    }
}
