package com.learnandroid.loginsqlite;

public class Mailman extends Person {

    private String password;

    public String getPassword() {
        return this.password;
    }

    public Mailman(String name, String email, String address, int balance, String password) {
        super(name, email, address, balance);
        this.password = password;
    }
}
