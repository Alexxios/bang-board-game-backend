package response.models;

import cards.PlayingCardName;

public record OnCardPlay (int getterIndex, int senderIndex, PlayingCardName cardName, int cardIndex){
}
