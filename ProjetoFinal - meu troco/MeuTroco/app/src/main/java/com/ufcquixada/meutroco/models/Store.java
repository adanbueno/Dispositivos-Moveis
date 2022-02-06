package com.ufcquixada.meutroco.models;

import java.util.List;

class Location {
    List<Double> coordinates;

    public List<Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "{" +
                "coordinates=" + coordinates +
                '}';
    }
}

public class Store {
    private String _id;
    private String name;
    private String email;
    private String address;
    private String number;
    private String telephone;
    private Location location;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", telephone='" + telephone + '\'' +
                ", location=" + location +
                '}';
    }

    public List<Double> getLocation() {
        return location.getCoordinates();
    }
}
