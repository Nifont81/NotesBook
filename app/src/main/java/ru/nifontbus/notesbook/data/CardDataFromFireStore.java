package ru.nifontbus.notesbook.data;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CardDataFromFireStore extends CardData {
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_IMAGE_IDX = "image_id";

    public CardDataFromFireStore(int pos, String title, String description, Date date, int picture) {
        super(pos, title, description, date, picture);
    }

    public CardDataFromFireStore(String id, String title, String description, Date date, int picture) {
        this(0, title, description, date, picture);
        setId(id);
    }

    public CardDataFromFireStore(String id, Map<String, Object> fields) {
        this(id, (String) fields.get(FIELD_TITLE), (String) fields.get(FIELD_DESCRIPTION),
                (Date) fields.get(FIELD_DATE), (int) fields.get(FIELD_IMAGE_IDX));
    }

    public CardDataFromFireStore(CardData cardData) {
        this(cardData.getId(), cardData.getTitle(), cardData.getDescription(),
                cardData.getDate(), cardData.getPicture());
    }


    public final Map<String, Object> getFields() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_TITLE, getTitle());
        fields.put(FIELD_DESCRIPTION, getDescription());
        fields.put(FIELD_DATE, getDate());
        fields.put(FIELD_IMAGE_IDX, getPicture());
        return Collections.unmodifiableMap(fields);

    }
}
