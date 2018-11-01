package ru.mrwinwon.poltindex.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Timeline implements Parcelable {
    private int idUser;
    private String name;
    private String avatar;
    private int type;
    private String text;
    private int like;
    private String fullDate;
    private boolean isYour;

    public Timeline(int idUser, String name, String avatar, int type, String text, int like, String fullDate, boolean isYour) {
        this.idUser = idUser;
        this.name = name;
        this.avatar = avatar;
        this.type = type;
        this.text = text;
        this.like = like;
        this.fullDate = fullDate;
        this.isYour = isYour;
    }

    protected Timeline(Parcel in) {
        idUser = in.readInt();
        name = in.readString();
        avatar = in.readString();
        type = in.readInt();
        text = in.readString();
        like = in.readInt();
        fullDate = in.readString();
        isYour = in.readByte() != 0;
    }

    public static final Creator<Timeline> CREATOR = new Creator<Timeline>() {
        @Override
        public Timeline createFromParcel(Parcel in) {
            return new Timeline(in);
        }

        @Override
        public Timeline[] newArray(int size) {
            return new Timeline[size];
        }
    };

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public boolean isYour() {
        return isYour;
    }

    public void setYour(boolean your) {
        isYour = your;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idUser);
        parcel.writeString(name);
        parcel.writeString(text);
        parcel.writeString(fullDate);
        parcel.writeString(avatar);
        parcel.writeByte((byte) (isYour ? 1 : 0));
        parcel.writeInt(like);
        parcel.writeInt(type);
    }
}
