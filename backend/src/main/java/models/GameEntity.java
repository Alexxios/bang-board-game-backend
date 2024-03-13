package models;

import cards.PlayingCard;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class GameEntity {

    public GameEntity(){}

    public GameEntity(int motionPlayerIndex, List<Player> players, List<PlayingCard> deck, String gameId) {
        this.motionPlayerIndex = motionPlayerIndex;
        this.players = players;
        this.deck = deck;
        this.discarded = new ArrayList<>();
        this.callbacks = new ArrayList<>();
        this.gameId = gameId;
    }

    public List<PlayingCard> nextMotion(){
        motionPlayerIndex = (motionPlayerIndex + 1) % players.size();

        ArrayList<PlayingCard> addedCards = new ArrayList<>();
        for (int i =  players.get(motionPlayerIndex).getCards().size(); i < players.get(motionPlayerIndex).getHealth(); ++i){
            players.get(motionPlayerIndex).receiveCard(deck.getLast());
            addedCards.add(deck.getLast());
            deck.removeLast();
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
        this.callbacks.removeFirst();
    }

    public void addCallback(Callback callback){
        this.callbacks.add(callback);
    }

    /**
     * Retrieves and removes first card from the {@code deck}. Use this anytime you interact with the {@code deck}.
     * @return first card from {@code deck}
     */
    public PlayingCard drawFirstCard() {
        if (deck.isEmpty()) resetDeck();
        PlayingCard card = deck.getFirst();
        deck.removeFirst();
        return card;
    }

    private void resetDeck() {
        deck.addAll(discarded);
        discarded.clear();
        Collections.shuffle(deck);
    }

    @Setter
    private int motionPlayerIndex;
    private List<Player> players;
    private List<PlayingCard> deck, discarded;
    @Setter
    private List<Callback> callbacks;
    private String gameId;
    @Setter
    private boolean wasBangPlayed;
}
