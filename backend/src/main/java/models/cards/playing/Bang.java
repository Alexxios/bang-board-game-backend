package models.cards.playing;

import callbacks.CallbackType;
import cards.PlayingCardName;
import cards.Suit;
import characters.Character;
import models.*;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("bangCardBean")
public class Bang extends ICard {

    private static final int copiesCount = 25;
    private final static List<Map.Entry<Suit, Integer>> cardTypesList = new ArrayList<>();

    static {
        cardTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Spades, 14));
        for (int i = 2; i <= 14; ++i){
            cardTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Diamonds, i));
        }
        for (int i = 2; i <= 9; ++i){
            cardTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Clubs, i));
        }
        for (int i = 12; i <= 14; ++i){
            cardTypesList.add(new AbstractMap.SimpleEntry<>(Suit.Hearts, i));
        }
    }

    public Bang(){
        super(copiesCount, cardTypesList);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        int sender = event.getSenderIndex(), getter = event.getGetterIndex(), playersCount = game.getPlayers().size();
        int weaponDistance = game.getPlayer(sender).getShootingDistance();
        int distance = Math.min(Math.abs(sender - getter), Math.abs(playersCount + sender - getter));
        Player senderPlayer = game.getPlayer(sender);
        Player getterPlayer = game.getPlayer(getter);

        if (game.isWasBangPlayed() || senderPlayer == getterPlayer){
            return new HandleEventResult(false, game);
        }

        if (senderPlayer.getCharacter() == Character.PaulRegret){
            distance++;
        }

        if (getterPlayer.getCharacter() == Character.RoseDoolan){
            distance--;
        }

        if (senderPlayer.getBuffs().isHasAim()){
            distance--;
        }

        if (game.getPlayer(getter).getBuffs().isHasMustang()){
            distance++;
        }

        if (distance > weaponDistance){
            return new HandleEventResult(false, game);
        }

        Callback callback = new Callback(event, CallbackType.Bang);
        game.getCallbacks().add(callback);

        if (game.getPlayer(sender).getCharacter() == Character.SlabTheKiller){
            game.getCallbacks().add(callback);
        }

        boolean canPlayMultipleBang = senderPlayer.getWeapon().getCardName() == PlayingCardName.Volcanic
                || senderPlayer.getCharacter() == Character.WillyTheKid;

        if (!canPlayMultipleBang){
            game.setWasBangPlayed(true);
        }

        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
