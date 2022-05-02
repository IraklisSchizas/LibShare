package com.learnandroid.loginsqlite;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //public static final String DBNAME = "Login3.db";
    public static final String userstbl = "users";
    public static final String booktbl = "booklist";
    public static final String profile = "profile";
    public static final String history = "history";
    public static final String book_request_list = "book_request_list";
    public static final String col1 ="username";
    public static final String col2 ="use";
    public static final String col3 ="book";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }
    private SQLiteDatabase database;

    public double appEarnings = 0;

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String table1 = "CREATE TABLE "+userstbl+"(username TEXT primary key, password TEXT)";
        String table2 = "CREATE TABLE "+booktbl+"(id INTEGER primary key AUTOINCREMENT,username TEXT,book TEXT, author TEXT,price INTEGER)";
        String table3 = "CREATE TABLE "+profile+"(username TEXT primary key, postal TEXT,balance DOUBLE,email TEXT)";
        String table4 = "CREATE TABLE "+history+"(code INTEGER primary key AUTOINCREMENT, username TEXT,use TEXT,book TEXT)";
        // Δημιουργία πινάκων admin
        String table5 = "CREATE TABLE "+book_request_list+"(id INTEGER primary key AUTOINCREMENT, username TEXT, book TEXT, author TEXT,price INTEGER)";
        //////////////////////////////////////////
        // Καταχώρηση πινάκων στη βάση
        MyDB.execSQL(table1);
        MyDB.execSQL(table2);
        MyDB.execSQL(table3);
        MyDB.execSQL(table4);
        MyDB.execSQL(table5);
        //αρχικοποίηση των τοιχείων του admin
        //String admin = " INSERT INTO users(username,password) values ('admin','admin')";
        Administrator admin = new Administrator("admin", "","",0,"admin");
        // καταχωρηση του admin στην βάση
        //MyDB.execSQL(admin);
        this.insertAdministrator(admin);
        ///////////////////////////////////////
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS "+userstbl);
        MyDB.execSQL("DROP TABLE IF EXISTS "+booktbl);
        MyDB.execSQL("DROP TABLE IF EXISTS "+profile);
        MyDB.execSQL("DROP TABLE IF EXISTS "+history);
        MyDB.execSQL("DROP TABLE IF EXISTS "+book_request_list);
        onCreate(MyDB);
    }

    //για πινακα user KAI πίνακα profile
    public Boolean insertUser(User user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", user.getName());
        contentValues.put("password", user.getPassword());

        ContentValues profileValues= new ContentValues();
        profileValues.put("postal", user.getAddress());
        profileValues.put("email", user.getEmail());
        profileValues.put("balance", user.getBalance());
        long result1 = MyDB.insert(userstbl, null, contentValues);
        long result2 = MyDB.insert(profile, null, profileValues);
        if(result1==-1 || result2==-1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertAdministrator(Administrator administrator) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", administrator.getName());
        contentValues.put("password", administrator.getPassword());

        ContentValues profileValues= new ContentValues();
        profileValues.put("postal", administrator.getAddress());
        profileValues.put("email", administrator.getEmail());
        profileValues.put("balance", administrator.getBalance());
        long result1 = MyDB.insert(userstbl, null, contentValues);
        long result2 = MyDB.insert(profile, null, profileValues);
        if(result1==-1 || result2==-1) {
            return false;
        } else {
            return true;
        }
    }

    //για πινακα book_request_list
    public Boolean insertBook(String username,Book book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues bookValues= new ContentValues();
        bookValues.put("username", username);
        bookValues.put("book", book.getName());
        bookValues.put("author", book.getAuthor());
        bookValues.put("price", book.getPrice());
        //long result = MyDB.insert(booktbl, null, bookValues);
        /*
         * Για να ελέγχονται τα trades από τον admin, περνιούνται πρώτα στον βοηθητικό πίνακα
         *   book_request_list που από εκεί θα γίνει accept ή reject από τον admin.
         */
        long result = MyDB.insert(book_request_list, null, bookValues);
        if(result==-1) return false;
        else
            return true;
    }

    /*
    //για πίνακα profile
    public Boolean insertProfile(String username, String postal, String email, String balance){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues profileValues= new ContentValues();
        profileValues.put("username", username);
        profileValues.put("postal", postal);
        profileValues.put("email", email);
        profileValues.put("balance", balance);
        long result = MyDB.insert(profile, null, profileValues);
        if(result==-1) return false;
        else
            return true;
    }
    */

    //ενημερωση profile
    public Boolean updateProfile(String username, String postal, String email, String balance){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues profileValues= new ContentValues();
        profileValues.put("username", username);
        profileValues.put("postal", postal);
        profileValues.put("email", email);
        profileValues.put("balance", balance);
        long result= MyDB.update(profile, profileValues, "username=?", new String[]{username});
        if(result==-1) return false;
        else
            return true;
    }
    //ενημερωση password άλλο table
    public Boolean updatePass(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result= MyDB.update(userstbl, contentValues, "username=?", new String[]{username});
        if(result==-1) return false;
        else
            return true;
    }

    // Select All Data
    public String[] SelectAllData() {
        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  book FROM " + booktbl;
            Cursor cursor = db.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()];
                    int i= 0;
                    do {
                        arrData[i] = cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            return arrData;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //profile check αλλιως cursor crash
    public Boolean checkProfile(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from profile where username = ? ", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkEmail(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select email from profile where username = ? ", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkPostal(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select postal from profile where username = ? ", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkBalance(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select balance from profile where username = ? ", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkBook(String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select id from booklist where book = ? ", new String[] {book});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //profile να παρω τιμες
    public Cursor getEmail(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select email from profile where username = ? ", new String[] {username});
        return cursor;
    }

    public Cursor getPostal(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select postal from profile where username = ? ", new String[] {username});
        return cursor;
    }

    public Cursor getBalance(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select balance from profile where username = ? ", new String[] {username});
        return cursor;
    }

    public Cursor getPass(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select password from users where username = ? ", new String[] {username});
        return cursor;
    }

    //book details
    public Cursor getAuthor(String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select author from booklist where book = ? ", new String[] {book});
        return cursor;
    }

    public Cursor getPrice(String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select price from booklist where book = ? ", new String[] {book});
        return cursor;
    }

    public Cursor getSeller(String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select username from booklist where book = ? ", new String[] {book});
        return cursor;
    }

    //για delete μετα την αγορα
    public void delete(String book,String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
         MyDB.delete(booktbl,"book=? and username=?",new String[] {book,username} );
    }

    //update balance μετα την αγορα
    public Boolean updateBalance(String username, double balance){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("balance", 0.99*balance);
        /*
        προσθήκη 2% επί την αξία την ανταλλαγής στο AppEarnings
        Η συνάρτηση καλείται δύο φορές οπότε χάνει 1% ο πωλητής και 1% ο αγοραστής
         */
        appEarnings += 0.01*balance;
        long result= MyDB.update(profile, contentValues, "username=?", new String[]{username});
        if(result==-1) return false;
        else
            return true;
    }

    //προσθηκη ιστορικου
    public Boolean insertHistoryBuy(String username,String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues historyValues= new ContentValues();
        historyValues.put("username", username);
        historyValues.put("use", "buy");
        historyValues.put("book", book);
        long result = MyDB.insert(history, null, historyValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean insertHistorySell(String username,String book){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues profileValues= new ContentValues();
        profileValues.put("username", username);
        profileValues.put("use", "sell");
        profileValues.put("book", book);
        long result = MyDB.insert(history, null, profileValues);
        if(result==-1) return false;
        else
            return true;
    }

    //create method to view data history
    public Cursor getHistoryList(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM  history where username =?" , new String[]{username});
        return data;
    }

    /* Βοηθητικές συναρτήσεις για τον admin */


    /*
    Αυτή η συνάρτηση επιστρέφει όλα τα trades to review στην πρώτη επιλογή του admin,
    σε μορφή ArrayList που όμως κάθε element είναι αντικείμενο κλάσης TradeToReview !!!
    Έπειτα θα χρησιμοποιηθεί το ArrayList για την εμφάνιση στο xml.
    */
    public ArrayList<TradeToReview> getTradesToReview() {

        SQLiteDatabase db = this.getWritableDatabase();

        // we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM " + book_request_list, null);

        // we are creating a new array list.
        ArrayList<TradeToReview> tradesArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // we are adding the data from cursor to our array list.
                tradesArrayList.add(new TradeToReview(cursor.getString(1), //username
                        cursor.getString(2), //bookName
                        cursor.getString(3), // author
                        cursor.getInt(4)));  // price
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // close our cursor and returning our array list.
        cursor.close();
        return tradesArrayList;
    }

    /*
    * Με αυτή τη συνάρτηση αποδεχόμαστε κάποιο trade, και
    *  το μεταφέρουμε από τον προσωρινό πίνακα trades_to_review
    * στον πίνακα booktbl που οι χρήστες μπορούν να κάνουν αναζήτηση
    */
    public void acceptTrade(String username, String bookName, String author, int price) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("book", bookName);
        values.put("author", author);
        values.put("price", price);
        /*
        Σε αυτό το σημείο πρέπει πρώτα να εισάγουμε τις τιμές στον booktbl και μετά
        να διαγράψουμε την εγγραφή από τον book_request_list.
        Θεωρούμε ότι η μοναδικότητα του βιβλίου βασίζεται σε username, bookName και author
        */
        db.insert(booktbl, null, values);
        db.delete(book_request_list,"username=? AND book=? AND author=?", new String[]{username, bookName, author});
        db.close();
    }

    /*
     * Με αυτή τη συνάρτηση απορρίπτουμε κάποιο trade, οπότε
     * δεν το μεταφέρουμε από τον προσωρινό πίνακα trades_to_review
     * στον πίνακα booktbl, και οι χρήστες δεν μπορούν να το αναζητήσουν
     */
    public void rejectTrade(String username, String bookName, String author, int price) {
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("book", bookName);
        values.put("author", author);
        values.put("price", price);
        /*
        Σε αυτό το σημείο αρκεί απλά να διαγράψουμε την εγγραφή από τον book_request_list
        Θεωρούμε ότι η μοναδικότητα του βιβλίου βασίζεται σε username, bookName και author
        */
        db.delete(book_request_list,"username=? AND book=? AND author=?", new String[]{username, bookName, author});
        db.close();
    }
}