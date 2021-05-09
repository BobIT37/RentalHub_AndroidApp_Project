package com.karayadar.app.models;

public class Shops {

    private String id, shopstatus, size, floors, address,us_id,shopimage,rent,description,simage;
    public Shops(){}

    public Shops(String id, String us_id, String shopstatus,String address, String floors, String rent, String size, String description, String shopimage, String simage) {
        this.id = id;
        this.shopstatus = shopstatus;
        this.size = size;
        this.floors = floors;
        this.address = address;
        this.rent = rent;
        this.us_id = us_id;
        this.description = description;
        this.shopimage = shopimage;
        this.simage = simage;
    }

    public String getId() {
        return id;
    }

    public String getshopstatus() {
        return shopstatus;
    }

    public String getsize() {
        return size;
    }

    public String getfloors() {
        return floors;
    }

    public String getaddress() {
        return address;
    }

    public String getus_id() {
        return us_id;
    }

    public String getshopimage() {
        return shopimage;
    }
    public String getimage2() {
        return simage;
    }

    public String getrent() {
        return rent;
    }

    public String getDescription() {
        return description;
    }
}

