package ru.nifontbus.notesbook;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import java.util.Date;

public class CardData implements Parcelable {
    private int id;
    private String title;       // заголовок
    private String description; // описание
    private boolean like;       // флажок
    private Date date;          // дата
    private @DrawableRes int imageResourceId;

    public CardData(int id, String title, String description,
                    boolean like, Date date, @DrawableRes int imageResourceId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.like = like;
        this.date = date;
        this.imageResourceId = imageResourceId;
    }

    protected CardData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
        imageResourceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
        dest.writeInt(imageResourceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public int getId() {
        return id;
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

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
