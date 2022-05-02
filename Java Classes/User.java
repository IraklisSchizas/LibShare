package com.learnandroid.loginsqlite;

public class User extends Person {

    private String password;

    public String getPassword() {
        return this.password;
    }

    public User(String name, String email, String address, int balance, String password) {
        super(name, email, address, balance);
        this.password = password;
    }
}
