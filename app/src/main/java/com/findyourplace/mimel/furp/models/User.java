package com.findyourplace.mimel.furp.models;

/**
 * Created by mimel on 12/09/17.
 */

public class User {
    String name;
    String city;
    String description;
    String email;

    public User(){

    }

    public User(String name, String city, String description, String email) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
