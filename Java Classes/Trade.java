package com.learnandroid.loginsqlite;

public class Trade {

    private  String code ,use ,book;

    public Trade (String cod,String type ,String bookname ){
        code=cod;
        use=type;
        book=bookname;
    }

    public String getCode() {
        return code;
    }
    public String getUse() {
        return use;
    }
    public String getBook() {
        return book;
    }
}
