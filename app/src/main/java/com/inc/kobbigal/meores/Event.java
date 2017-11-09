package com.inc.kobbigal.meores;

/**
 * Created by Kobbi.Gal on 04/11/2017.
 */

public class Event {

    private int id;
    private String name;
    private String location;
    private String date;
    private String time;
    private int numberOfAttendees;
    private int statusId;

    public Event() {
    }

    Event(int id, String name, String location, String date, String time, int numberOfAttendees, int statusId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.numberOfAttendees = numberOfAttendees;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    //private int totalCost;

}
