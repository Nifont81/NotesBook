package ru.nifontbus.notesbook;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import java.util.Date;

public class CardData implements Parcelable {
    private String id;          // идентификатор
    private int pos;            // позиция в списке
    private String title;       // заголовок
    private String description; // описание
    private boolean like;       // флажок
    private Date date;          // дата
    private @DrawableRes int picture;

    public CardData(int pos, String title, String description,
                    boolean like, Date date, @DrawableRes int picture) {
        this.pos = pos;
        this.title = title;
        this.description = description;
        this.like = like;
        this.date = date;
        this.picture = picture;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
        picture = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
        dest.writeInt(picture);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }


}
