package ru.nifontbus.notesbook;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);
    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();

}
