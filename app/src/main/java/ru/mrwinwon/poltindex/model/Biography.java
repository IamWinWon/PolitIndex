package ru.mrwinwon.poltindex.model;

public class Biography {
    private int idPerson;
    private String bio;
    private String dateBirth;

    public Biography(int idPerson, String bio, String dateBirth) {
        this.idPerson = idPerson;
        this.bio = bio;
        this.dateBirth = dateBirth;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
}
