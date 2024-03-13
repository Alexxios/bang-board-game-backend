package models.cards.playing;

import callbacks.CallbackType;
import cards.PlayingCard;
import characters.Character;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import response.models.NextMotionResult;
import server.BackendApplication;
import server.ws.controllers.GameEventsController;

import java.sql.SQLOutput;

@Component("bangCardBean")
public class Bang extends ICard {

    private static final int copiesCount = 25;

    public Bang(){
        super(copiesCount);
    }

    @Override
    public HandleEventResult handlerEvent(GameEntity game, Event event) {
        if (game.isWasBangPlayed()){
            return new HandleEventResult(false, game);
        }

        int sender = event.getSenderIndex(), getter = event.getGetterIndex(), playersCount = game.getPlayers().size();
        int weaponDistance = game.getPlayer(sender).getShootingDistance();
        int distance = Math.min(Math.abs(sender - getter), Math.abs(playersCount + sender - getter));
        Player senderPlayer = game.getPlayer(sender);

        if (senderPlayer.getCharacter() == Character.ColdbloodedRosie){
            distance++;
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

        if (game.getPlayer(sender).getCharacter() == Character.AngelEyes){
            game.getCallbacks().add(callback);
        }

        boolean canPlayMultipleBang = senderPlayer.getWeapon() == PlayingCard.Volcanic
                || senderPlayer.getCharacter() == Character.BillyTheKid;

        if (!canPlayMultipleBang){
            game.setWasBangPlayed(true);
        }

        game.setMotionPlayerIndex(event.getGetterIndex());
        return new HandleEventResult(true, game);
    }
}
