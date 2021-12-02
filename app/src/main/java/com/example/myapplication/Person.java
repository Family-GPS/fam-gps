package com.example.myapplication;

import android.location.Location;

import java.io.Serializable;


public class Person implements Serializable {
    public String identifier;
    public double latitude;
    public double longitude;
    public double speed;
    public double time; //change to DateTime later

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


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
