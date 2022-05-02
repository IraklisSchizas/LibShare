package com.learnandroid.loginsqlite;

public class TradeToReview {

    // Τοπικές μεταβλητές για το Trade που θέλουμε να υλοποιήσουμε

    int id;
    String username;
    String bookName;
    String author;
    int price;


    // creating getter and setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeUsername() {
        return username;
    }

    public void setTradeUsername(String username) {
        this.username = username;
    }

    public String getTradeBookName() {
        return bookName;
    }

    public void setTradeBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getTradeAuthor() {
        return author;
    }

    public void setTradeAuthor(String author) {
        this.author = author;
    }

    public String getTradePrice() { return Integer.toString(price); }

    public void setTradePrice(int price) {
        this.price = price;
    }

    // constructor
    public TradeToReview(String username, String bookName, String author, int price) {
        this.username = username;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }
}
