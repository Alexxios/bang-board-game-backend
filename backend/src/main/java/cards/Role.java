package cards;

import lombok.Getter;

public enum Role {
    Sheriff(4),
    Assistant(4),
    Bandit(4),

    Renegat(4);

    Role(int health) {
        this.health = health;
    }

    @Getter
    private final int health;
}
