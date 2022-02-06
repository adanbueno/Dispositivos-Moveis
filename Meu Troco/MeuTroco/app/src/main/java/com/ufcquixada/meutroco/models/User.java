package com.ufcquixada.meutroco.models;

public class User {
    private String _id;
    private String name;
    private String email;
    private String cpf;
    private double balance;

    public User(String _id, String name, String email, String cpf, double balance) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.balance = balance;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalanceNegative(double value) {
        this.balance -= value;
    }

    public void setBalancePositive(double value) {
        this.balance += value;
    }

    @Override
    public String toString() {
        return "{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", balance=" + balance +
                '}';
    }
}
