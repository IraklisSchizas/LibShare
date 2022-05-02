package com.learnandroid.loginsqlite;

public class Person {

    protected String name, email, address;
    protected double balance;
    public Boolean isAdministrator = false;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Person(String name, String email, String address, double balance) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.balance = balance;
    }
}
