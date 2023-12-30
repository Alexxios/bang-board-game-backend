package models;

import cards.Role;
import lombok.Getter;
import lombok.Setter;
import models.cards.ICard;

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
    }

    public void getCard(ICard card){
        cards.add(card);
    }

    public void useCard(int index){
        cards.remove(index);
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void updateDistance(int delta){
        distance += delta;
    }

    private final Role role;
    private int health;
    private int distance;
    @Setter
    private int shootDamage;
    @Setter
    private List<ICard> cards;
}
