package models;

import cards.Role;
import lombok.Getter;
import models.cards.Card;

import java.util.List;

@Getter
public class Player {
    public Player(Role role, int health, int distance, List<Card> cards) {
        this.role = role;
        this.health = health;
        this.distance = distance;
        this.cards = cards;
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

    private Role role;
    private int health;
    private int distance;
    private List<Card> cards;
}
