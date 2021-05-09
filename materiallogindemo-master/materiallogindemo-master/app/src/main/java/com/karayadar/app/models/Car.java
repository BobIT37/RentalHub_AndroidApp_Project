package com.karayadar.app.models;

public class Car {

    private int id;
    private String status;
    private String maker;
    private String model;
    private String color;
    private String rent;
    private String description;
    private int userid;



    public Car(String status, String maker, String model, String color, String rent, String description, int userid) {
        this.status = status;
        this.maker = maker;
        this.model= model;
        this.color = color;
        this.rent = rent;
        this.description = description;
        this.userid = userid;

    }

/*
    public User(int id, String name, String address, String email, String phoneno, String password){
        this.id = id;
        this.status = name;
        this.address = address;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;

    }
*/

    /*public User(int id, String name, String email, String password, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }*/

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getRent() {
        return rent;
    }

    public String getDescription() {
        return description;
    }

    public int getUserid() {
        return userid;
    }
}


