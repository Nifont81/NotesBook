package ru.nifontbus.notesbook;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}
