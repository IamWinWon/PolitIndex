package ru.mrwinwon.poltindex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Commentary implements Parcelable {
    private int idComment;
    private String fullDate;
    private String text;
    private String avatar;
    private String name;
    private boolean itSelf;

    private int itemCount;
    private ArrayList<Figure> figures = new ArrayList<>();

    public Commentary(int idComment, String fullDate, String text, String avatar, String name, boolean itSelf, int itemCount, ArrayList<Figure> figures) {
        this.idComment = idComment;
        this.fullDate = fullDate;
        this.text = text;
        this.avatar = avatar;
        this.name = name;
        this.itSelf = itSelf;
        this.itemCount = itemCount;
        this.figures = figures;
    }

    protected Commentary(Parcel in) {
        idComment = in.readInt();
        fullDate = in.readString();
        text = in.readString();
        avatar = in.readString();
        name = in.readString();
        itSelf = in.readByte() != 0;
        in.readTypedList(figures, Figure.CREATOR);
    }

    public static final Creator<Commentary> CREATOR = new Creator<Commentary>() {
        @Override
        public Commentary createFromParcel(Parcel in) {
            return new Commentary(in);
        }

        @Override
        public Commentary[] newArray(int size) {
            return new Commentary[size];
        }
    };

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isItSelf() {
        return itSelf;
    }

    public void setItSelf(boolean itSelf) {
        this.itSelf = itSelf;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idComment);
        parcel.writeString(fullDate);
        parcel.writeString(text);
        parcel.writeString(avatar);
        parcel.writeString(name);
        parcel.writeByte((byte) (itSelf ? 1 : 0));
    }
}
