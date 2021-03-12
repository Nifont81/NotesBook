package ru.nifontbus.notesbook;

import java.time.LocalDate;

public class Note {
    private long id;
    private String name;
    private String text;
    private LocalDate date;

    public Note(String name, String text, LocalDate date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
