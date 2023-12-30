package models;

import lombok.Getter;

@Getter
public class Event {

    private int senderIndex;
    private int getterIndex;
    private CardDescription cardDescription;
    private int cardIndex;
}
