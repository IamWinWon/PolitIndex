package ru.mrwinwon.poltindex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Figure implements Parcelable, Serializable {

    private int like;
    private int dislike;
    private int rating;
    private int isLiked;

    public Figure() {
        super();
    }

    protected Figure(Parcel in) {
        like = in.readInt();
        dislike = in.readInt();
        rating = in.readInt();
        isLiked = in.readInt();
    }

    public Figure(int like, int dislike, int rating, int isLiked) {
        this.like = like;
        this.dislike = dislike;
        this.rating = rating;
        this.isLiked = isLiked;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public static Creator<Figure> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Figure> CREATOR = new Creator<Figure>() {
        @Override
        public Figure createFromParcel(Parcel in) {
            return new Figure(in);
        }

        @Override
        public Figure[] newArray(int size) {
            return new Figure[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(like);
        parcel.writeInt(dislike);
        parcel.writeInt(rating);
        parcel.writeInt(isLiked);
    }
}
