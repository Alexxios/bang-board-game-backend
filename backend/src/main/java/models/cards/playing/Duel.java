package models.cards.playing;

import callbacks.CallbackType;
import cards.PlayingCardName;
import cards.Suit;
import models.PlayingCard;
import characters.Character;
import models.*;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component("duelCardBean")
public class Duel extends ICard{
    public static final int copiesCount = 3;

    private final static List<Map.Entry<Suit, Integer>> cardTypesList = List.of(
            new AbstractMap.SimpleEntry<>(Suit.Diamonds, 12),
            new AbstractMap.SimpleEntry<>(Suit.Spades, 11),
            new AbstractMap.SimpleEntry<>(Suit.Clubs, 8)
    );

    public Duel(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (event.getGetterIndex() == event.getSenderIndex()){
            return new HandleEventResult(false, game);
        }

        int sender = event.getSenderIndex(), getter = event.getGetterIndex();
        int bangCardsCount = countCardsForDuel(sender, game) + countCardsForDuel(getter, game) + 1;


        for (int i = 0; i < bangCardsCount; ++i){
            int getterIndex = event.getGetterIndex();
            if (i % 2 == 1){
                getterIndex = event.getSenderIndex();
            }
            Event newEvent = new Event(event.getSenderIndex(), getterIndex, event.getCardDescription(), event.getCardIndex());
            Callback callback = new Callback(newEvent, CallbackType.Duel);
            game.getCallbacks().add(callback);
        }

        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }

    int countCardsForDuel(int playerIndex, GameEntity game){
        Player player = game.getPlayer(playerIndex);
        int count = 0;
        for (PlayingCard card : player.getCards()){
            if (card.getCardName() == PlayingCardName.Bang || player.getCharacter() == Character.CalamityJanet && card.getCardName() == PlayingCardName.Miss){
                count++;
            }
        }

        return count;
    }
}
