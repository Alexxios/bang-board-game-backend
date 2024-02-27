package models;

import cards.PlayingCard;
import cards.Role;
import lombok.Getter;
import lombok.Setter;

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

    public void getHealth(int value){
        health = Math.max(health + value, maxHealth);
    }

    public void updateDistance(int delta){
        distance += delta;
    }

    private final Role role;
    private int health;
    private int maxHealth;
    private int distance;
    @Setter
    private int shootDamage;
    @Setter
    private List<PlayingCard> cards;
}
