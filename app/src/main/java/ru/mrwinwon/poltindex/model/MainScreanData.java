package ru.mrwinwon.poltindex.model;

public class MainScreanData {
    private int titleCounts;
    private int personCounts;
    private Person[] people;
    private Event[] events;

    public MainScreanData(int titleCounts, int personCounts, Person[] people, Event[] events) {
        this.titleCounts = titleCounts;
        this.personCounts = personCounts;
        this.people = people;
        this.events = events;
    }

    public int getTitleCounts() {
        return titleCounts;
    }

    public void setTitleCounts(int titleCounts) {
        this.titleCounts = titleCounts;
    }

    public int getPersonCounts() {
        return personCounts;
    }

    public void setPersonCounts(int personCounts) {
        this.personCounts = personCounts;
    }

    public Person[] getPeople() {
        return people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
