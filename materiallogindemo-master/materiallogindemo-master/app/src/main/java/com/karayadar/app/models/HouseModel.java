package com.karayadar.app.models;

public class HouseModel {

    private String id, housestatus, rent, furnished, garage, userid, image, size, address, description, bedroom, floors,simage;
    public HouseModel(){}

    public HouseModel(String id, String userid, String housestatus, String garage, String furnished, String size, String rent, String description, String image, String address, String bedroom, String floors , String simage) {
        this.id = id;
        this.housestatus = housestatus;
        this.rent = rent;
        this.furnished = furnished;
        this.garage = garage;
        this.size = size;
        this.userid = userid;
        this.address= address;
        this.bedroom=bedroom;
        this.floors = floors;
        this.description = description;
        this.image = image;
        this.simage = simage;
    }

    public String getId() {
        return id;
    }

    public String gethousestatus() {
        return housestatus;
    }

    public String getrent() {
        return rent;
    }

    public String getfurnished() {
        return furnished;
    }

    public String getgarage() {
        return garage;
    }

    public String getuserid() {
        return userid;
    }

    public String getImage() {
        return image;
    }
    public String getsimage() {
        return simage;
    }

    public String getsize() {
        return size;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getfloors() {
        return floors;
    }

    public String getaddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}