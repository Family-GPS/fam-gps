package com.example.myapplication;

public class Person {
    public String name;
    public Integer age;
    public Location loc;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }


    Person(String name, Integer age, Location loc){
        this.name = name;
        this.age = age;
        this.loc = loc;
    }
    Person(){}
}
