package ru.mrwinwon.poltindex.model;

public class Graph {
    private String date;
    private int likes;
    private int disLikes;
    private int rating;
    private int comments;
    private String fullDate;

    public Graph(String date, int likes, int rating, int comments, String fullDate, int disLike) {
        this.date = date;
        this.likes = likes;
        this.rating = rating;
        this.comments = comments;
        this.fullDate = fullDate;
        this.disLikes = disLike;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(int disLikes) {
        this.disLikes = disLikes;
    }
}
