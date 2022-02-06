package com.ufcquixada.meutroco.models;

public class Transfer {
    String _id;
    String name;
    double value;

    public Transfer(String _id, String name, double value) {
        this._id = _id;
        this.name = name;
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
