package com.karayadar.app.models;

public class Apartments {

    private String id, status, bedrooms, floors, elevator,userid,image,rent,description,address,simage;
    public Apartments(){}

    public Apartments(String id, String userid, String status,String elevator, String floors, String rent, String bedrooms, String description,String address, String image, String simage) {
        this.id = id;
        this.status = status;
        this.bedrooms = bedrooms;
        this.floors = floors;
        this.elevator = elevator;
        this.rent = rent;
        this.address = address;
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

    public String getbedrooms() {
        return bedrooms;
    }

    public String getfloors() {
        return floors;
    }

    public String getelevator() {
        return elevator;
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

    public String getrent() {
        return rent;
    }
    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
