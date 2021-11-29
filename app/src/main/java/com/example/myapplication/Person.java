package com.example.myapplication;

public class Person {


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String identifier;
    public double latitude;
    public double longitude;
    public double speed;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double time; //change to DateTime later

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


    public Person(String identifier, double latitude, double longitude, double speed, double time) {
        this.identifier = identifier;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.time = time;

    }
    Person(){}
}
