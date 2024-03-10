package models;

import lombok.Getter;

@Getter
public class Event {
    public Event(){}

    public Event(int senderIndex, int getterIndex, CardDescription cardDescription, int cardIndex) {
        this.senderIndex = senderIndex;
        this.getterIndex = getterIndex;
        this.cardDescription = cardDescription;
        this.cardIndex = cardIndex;
    }

    private int senderIndex;
    private int getterIndex;
    private CardDescription cardDescription;
    private int cardIndex;
}
