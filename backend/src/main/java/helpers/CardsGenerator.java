package helpers;
import cards.PlayingCard;

import java.util.*;

public class CardsGenerator {

    private static final int maxRepeat = 5;

    public static List<PlayingCard> generateCards(){
        ArrayList<PlayingCard> result = new ArrayList<>();
        Random random = new Random();
        for (PlayingCard card : PlayingCard.values()){
            int count = random.nextInt(1, maxRepeat + 1);
            for (int i = 0; i < count; ++i){
                result.add(card);
            }
        }
        Collections.shuffle(result);
        return result;
    }
}
