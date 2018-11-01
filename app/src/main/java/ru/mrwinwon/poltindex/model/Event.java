package ru.mrwinwon.poltindex.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Event implements Parcelable, Serializable {
    private int idEvent;
    private String title;
    private String icon;

    public Event(int idEvent, String title, String icon) {
        this.idEvent = idEvent;
        this.title = title;
        this.icon = icon;
    }

    protected Event(Parcel in) {
        idEvent = in.readInt();
        title = in.readString();
        icon = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idEvent);
        parcel.writeString(title);
        parcel.writeString(icon);
    }
}
