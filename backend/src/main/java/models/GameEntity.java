package models;

import cards.PlayingCard;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class GameEntity {

    public GameEntity(){}

    public GameEntity(int motionPlayerIndex, List<Player> players, List<PlayingCard> cards) {
        this.motionPlayerIndex = motionPlayerIndex;
        this.players = players;
        this.cards = cards;
        this.callback = new Callback();
    }

    public void nextMotion(){
        motionPlayerIndex = (motionPlayerIndex + 1) % players.size();

        for (int i = 0; i < players.get(motionPlayerIndex).getHealth(); ++i){
            players.get(motionPlayerIndex).getCard(cards.getLast());
            cards.removeLast();
        }
    }

    public void useCard(int playerIndex, int cardIndex){
        players.get(playerIndex).useCard(cardIndex);
    }

    public Player getPlayer(int playerIndex){
        return players.get(playerIndex);
    }

    public void checkPlayer(int playerIndex){
        if (players.get(playerIndex).getHealth() <= 0){
            players.remove(playerIndex);
        }
    }

    public void resetCallback(){
        this.callback.reset();
    }

    @Setter
    private int motionPlayerIndex;
    private List<Player> players;
    private List<PlayingCard> cards;
    @Setter
    private Callback callback;
}
