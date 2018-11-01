package ru.mrwinwon.poltindex.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    // Person parametres
    private int idPerson;
    private String firstName;
    private String middleName;
    private String secondName;
    private String avatar;
    private String avatarSmall;
    private int isLiked;

    // today
    private int likes;
    private int dislikes;
    private int rating;

    // graph
    private Graph[] arr;

    public Person(int idPerson, String firstName, String middleName, String secondName, String avatar, String avatarSmall, int isLiked, int likes, int dislikes, int rating, Graph[] arr) {
        this.idPerson = idPerson;
        this.firstName = firstName;
        this.middleName = middleName;
        this.secondName = secondName;
        this.avatar = avatar;
        this.avatarSmall = avatarSmall;
        this.isLiked = isLiked;
        this.likes = likes;
        this.dislikes = dislikes;
        this.rating = rating;
        this.arr = arr;
    }

    protected Person(Parcel in) {
        idPerson = in.readInt();
        firstName = in.readString();
        middleName = in.readString();
        secondName = in.readString();
        avatar = in.readString();
        avatarSmall = in.readString();
        isLiked = in.readInt();
        likes = in.readInt();
        dislikes = in.readInt();
        rating = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarSmall() {
        return avatarSmall;
    }

    public void setAvatarSmall(String avatarSmall) {
        this.avatarSmall = avatarSmall;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Graph[] getGraph() {
        return arr;
    }

    public void setGraph(Graph[] arr) {
        this.arr = arr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idPerson);
        parcel.writeString(firstName);
        parcel.writeString(middleName);
        parcel.writeString(secondName);
        parcel.writeString(avatar);
        parcel.writeString(avatarSmall);
        parcel.writeInt(isLiked);
        parcel.writeInt(likes);
        parcel.writeInt(dislikes);
        parcel.writeInt(rating);
    }
}
