package ru.nifontbus.notesbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class Note implements Parcelable {
    private int id;
    private String name;
    private String text;
    //private LocalDate date;

    public Note(int id, String name, String text) {
        this.name = name;
        this.text = text;
        //this.date = date;
    }


    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readString();
        text = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

//    public LocalDate getDate() {
//        return date;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(text);
    }
}
