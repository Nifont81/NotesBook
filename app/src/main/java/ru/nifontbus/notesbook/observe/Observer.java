package ru.nifontbus.notesbook.observe;

import ru.nifontbus.notesbook.CardData;

public interface Observer {
    void updateCardData(CardData cardData);
}
