package com.karayadar.app.models;

public class Owner {


    private String name;
    private String address;
    private String email;
    private String phoneno;
    private String rent;
    private String description;
    private int userid;



    public Owner(String name, String address, String email, String phoneno) {
        this.name = name;
        this.address = address;
        this.email= email;
        this.phoneno = phoneno;


    }



    public String getname() {
        return name;
    }

    public String getaddress() {
        return address;
    }

    public String getemail() {
        return email;
    }

    public String getphoneno() {
        return phoneno;
    }


}


