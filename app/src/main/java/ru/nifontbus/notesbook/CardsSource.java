package ru.nifontbus.notesbook;

public interface CardsSource {
    CardData getCardData(int position);
    CardData getItemAt(int idx);
    int getItemsCount();
    void remove(int position);
    void update(CardData cardData);
    void add(CardData cardData);
    void clear();

}
