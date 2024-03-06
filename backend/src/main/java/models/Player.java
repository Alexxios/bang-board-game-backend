package models;

import cards.PlayingCard;
import cards.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    public Player() {
        this.role = Role.Sheriff;
    }

    public Player(Role role) {
        this.role = role;
        this.health = role.getHealth();
        this.distance = 1;
        this.shootDamage = 1;
        this.maxHealth = role.getHealth();
        this.weapon = PlayingCard.Colt;
        this.cards = new ArrayList<>();
    }

    public void getCard(PlayingCard card){
        cards.add(card);
    }

    public void useCard(int index){
        cards.remove(index);
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void updateHealth(int value){
        health = Math.max(health + value, maxHealth);
    }

    public void updateDistance(int delta){
        distance += delta;
    }

    private final Role role;
    private int health;
    private int maxHealth;
    @Setter
    private PlayingCard weapon;
    private int distance;
    @Setter
    private int shootDamage;
    @Setter
    private List<PlayingCard> cards;
}
