package models.cards.weapons;

import models.cards.Weapon;

public class Remington extends Weapon {
    static {
        distance = 3;
    }
    public Remington(Rank rank, Suit suit) {
        super(rank, suit);
    }
}
