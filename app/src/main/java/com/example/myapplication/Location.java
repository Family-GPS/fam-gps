package com.example.myapplication;


public class Location {
    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int id =0;
    public double latitude;
    public double longitude;
    public double speed;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String identifier;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public Location(double latitude, double longitude, double speed, String identifier) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.identifier = identifier;
        this.id = id++;

    }
    Location(){}
}
