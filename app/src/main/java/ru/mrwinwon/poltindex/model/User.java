package ru.mrwinwon.poltindex.model;

public class User {

    private int idUser;
    private String name;
    private String email;
    private String profession;
    private String telegram;
    private String dateBirth;
    private String avatarUrl;
    private String sex;

    public User(String name, String email, String profession, String telegram, String dateBirth) {
        this.name = name;
        this.email = email;
        this.profession = profession;
        this.telegram = telegram;
        this.dateBirth = dateBirth;
    }

    public User(String name, String email, String profession, String telegram, String dateBirth, String avatarUrl, String sex) {
        this.name = name;
        this.email = email;
        this.profession = profession;
        this.telegram = telegram;
        this.dateBirth = dateBirth;
        this.avatarUrl = avatarUrl;
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
