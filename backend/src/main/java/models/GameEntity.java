package models;

import cards.PlayingCard;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.digester.ArrayStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameEntity {

    public GameEntity(){}

    public GameEntity(int motionPlayerIndex, List<Player> players, List<PlayingCard> cards, String gameId) {
        this.motionPlayerIndex = motionPlayerIndex;
        this.players = players;
        this.cards = cards;
        this.callback = new Callback();
        this.gameId = gameId;
    }

    public List<PlayingCard> nextMotion(){
        motionPlayerIndex = (motionPlayerIndex + 1) % players.size();

        ArrayList<PlayingCard> addedCards = new ArrayList<>();
        for (int i =  players.get(motionPlayerIndex).getCards().size(); i < players.get(motionPlayerIndex).getHealth(); ++i){
            players.get(motionPlayerIndex).getCard(cards.getLast());
            addedCards.add(cards.getLast());
            cards.removeLast();
        }
        return addedCards;
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
    private String gameId;
}
