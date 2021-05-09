package com.karayadar.app.models;

public class Items {

    private String id, status, rent, model, maker,userid,image,color,description,simage;
    public Items(){}

    public Items(String id, String userid, String status,String maker, String model, String color, String rent, String description, String image,String simage) {
        this.id = id;
        this.status = status;
        this.rent = rent;
        this.model = model;
        this.maker = maker;
        this.color = color;
        this.userid = userid;
        this.description = description;
        this.image = image;
        this.simage = simage;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRent() {
        return rent;
    }

    public String getModel() {
        return model;
    }

    public String getMaker() {
        return maker;
    }

    public String getUserid() {
        return userid;
    }

    public String getImage() {
        return image;
    }

    public String getImage2() {
        return simage;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
}
