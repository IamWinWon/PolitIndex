package ru.mrwinwon.poltindex.model;

import java.io.Serializable;

public class Language extends TransleteLocale implements Serializable {
    private int idLanguage;
    private String topName;
    private String bottomName;
    private String locale;

    public Language(int idLanguage, String topName, String bottomName, String locale) {
        this.idLanguage = idLanguage;
        this.topName = topName;
        this.bottomName = bottomName;
        this.locale = locale;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
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
