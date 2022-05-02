package com.learnandroid.loginsqlite;

public class Administrator extends Person {

    private String password;
    public Boolean isAdministrator = true;

    public String getPassword() {
        return this.password;
    }

    public Administrator(String name, String email, String address, int balance, String password) {
        super(name, email, address, balance);
        this.password = password;
    }
}
