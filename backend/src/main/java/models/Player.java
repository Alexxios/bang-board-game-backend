package models;

import cards.Role;
import lombok.Getter;
import lombok.Setter;
import models.cards.Card;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
public class Player {

    public Player(Role role) {
        this.role = role;
        this.health = 0;
        this.distance = 1;
    }

    public void getCard(Card card){
        cards.add(card);
    }

    public void useCard(int index){
        cards.remove(index);
    }

    public void getDamage(int damage){
        health -= damage;
    }

    public void updateDistance(int delta){
        distance += delta;
    }

    private final Role role;
    private int health;
    private int distance;
    @Setter
    private List<Card> cards;
}
