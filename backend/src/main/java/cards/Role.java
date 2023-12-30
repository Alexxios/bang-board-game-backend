package cards;

import lombok.Getter;

public enum Role {
    Sheriff(4),
    Assistant(4),
    Bandi(4);

    Role(int health) {
        this.health = health;
    }

    @Getter
    private final int health;
}
