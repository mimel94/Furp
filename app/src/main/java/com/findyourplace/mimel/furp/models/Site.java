package com.findyourplace.mimel.furp.models;

/**
 * Created by mimel on 12/09/17.
 */

public class Site {

    String name;
    String location;
    String city;
    String description;
    String type;

    public Site() {

    }

    public Site(String name, String location, String city, String description, String type) {
        this.name = name;
        this.location = location;
        this.city = city;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
