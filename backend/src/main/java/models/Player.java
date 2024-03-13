package models;

import cards.PlayingCard;
import cards.Role;
import characters.Character;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    public Player() {
        role = Role.Sheriff;
    }

    public Player(Role role, Character character) {
        this.role = role;
        this.health = role.getHealth();
        this.shootingDistance = 1;
        this.maxHealth = role.getHealth();
        this.weapon = PlayingCard.Colt;
        this.cards = new ArrayList<>();
        this.buffs = new PlayerBuffs();
        this.character = character;
    }

    public void receiveCard(PlayingCard card){
        cards.add(card);
    }

    public void useCard(int index){
        cards.remove(index);
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void updateHealth(int value){
        health = Math.min(health + value, maxHealth);
    }

    private final Role role;
    private int health;
    private int maxHealth;
    @Setter
    private PlayingCard weapon;
    @Setter
    private int shootingDistance;
    private PlayerBuffs buffs;
    @Setter
    private List<PlayingCard> cards;
    private Character character;
}
