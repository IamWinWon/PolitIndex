package ru.mrwinwon.poltindex.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressLint("ParcelCreator")
public class Translete implements Parcelable, Serializable {
    private ArrayList<Country> countries;
    private ArrayList<Language> languages;

    public Translete(ArrayList<Country> countries, ArrayList<Language> languages) {
        this.countries = countries;
        this.languages = languages;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
