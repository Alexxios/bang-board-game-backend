package models;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Event {
    public Event(){}

    public Event(int senderIndex, int getterIndex, CardDescription cardDescription, int cardIndex) {
        this.senderIndex = senderIndex;
        this.getterIndex = getterIndex;
        this.cardDescription = cardDescription;
        this.cardIndex = cardIndex;
    }

    @Setter
    private int senderIndex;
    @Setter
    private int getterIndex;
    private CardDescription cardDescription;
    private int cardIndex;
}
