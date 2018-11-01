package ru.mrwinwon.poltindex.model;

import java.io.Serializable;

public class Country extends TransleteLocale implements Serializable {
    private int idCountry;
    private String topName;
    private String bottomName;
    private String locale;

    public Country(int idCountry, String topName, String bottomName, String locale) {
        this.idCountry = idCountry;
        this.topName = topName;
        this.bottomName = bottomName;
        this.locale = locale;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getBottomName() {
        return bottomName;
    }

    public void setBottomName(String bottomName) {
        this.bottomName = bottomName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
