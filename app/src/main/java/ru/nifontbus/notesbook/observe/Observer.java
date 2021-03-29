package ru.nifontbus.notesbook.observe;

import ru.nifontbus.notesbook.data.CardData;

public interface Observer {
    void updateCardData(CardData cardData);
}
