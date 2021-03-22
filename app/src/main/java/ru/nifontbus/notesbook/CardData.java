package ru.nifontbus.notesbook;

public class CardData {
    private String title;       // заголовок
    private String description; // описание
    private boolean like;       // флажок

    public CardData(String title, String description, boolean like) {
        this.title = title;
        this.description = description;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLike() {
        return like;
    }
}