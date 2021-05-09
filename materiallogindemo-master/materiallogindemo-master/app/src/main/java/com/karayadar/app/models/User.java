package com.karayadar.app.models;



public class User {

    private int id;
    private String name;
    private String address;
    private String email;
    private String phoneno;
    private String password;


    public User(String name, String address, String email, String phoneno, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;

    }

    public User(int id, String name, String address, String email){
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;

    }

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getAddress() {
        return address;
    }
    public String getPhoneno() {
        return phoneno;
    }
}
