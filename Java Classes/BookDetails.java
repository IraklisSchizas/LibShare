package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookDetails extends AppCompatActivity {
    FloatingActionButton detailstohome;
    Button buyButton;
    EditText booktext, author,price,usernametext;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        detailstohome =(FloatingActionButton )findViewById(R.id.home4);
        booktext = (EditText) findViewById(R.id.editTextTextBookName);
        author = (EditText) findViewById(R.id.editTextTextAuthor);
        price = (EditText) findViewById(R.id.editTextTextPrice);
        usernametext = (EditText) findViewById(R.id.editTextTextSeller);
        buyButton = (Button) findViewById(R.id.buy);

        DB = new DBHelper(this);
        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");
        final String book= intent.getStringExtra("book");


        //γραφω στα text τα στοιχεια
        Cursor cursor = DB.getAuthor(book);
        cursor.moveToFirst();
        author.setText(cursor.getString(0));

        Cursor cursor2 = DB.getPrice(book);
        cursor2.moveToFirst();
        price.setText(cursor2.getString(0));
        Integer pricetag=Integer.parseInt(cursor2.getString(0));

        Cursor cursor3 = DB.getSeller(book);
        cursor3.moveToFirst();
        usernametext.setText(cursor3.getString(0));
        //παιρνω το ονομα του seller απο πανω
        String seller = usernametext.getText().toString();

        booktext.setText(book);

        //balance user
        Cursor cursor4 = DB.getBalance(user);
        cursor4.moveToFirst();
        Integer balance=Integer.parseInt(cursor4.getString(0));

        //balance seller
         Cursor cursor5 = DB.getBalance(seller);
         cursor5.moveToFirst();
         Integer seller_balance=Integer.parseInt(cursor5.getString(0));

         double wallet = balance-pricetag;
         double seller_wallet= (seller_balance+pricetag);

        //button for home
        detailstohome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
               intent.putExtra("username",user);
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                if(wallet<0) {
                    Toast.makeText(BookDetails.this, "Not enough balance", Toast.LENGTH_SHORT).show();
                }
                    else{
                       DB.delete(book, seller);
                       DB.insertHistoryBuy(user,book);
                       DB.insertHistorySell(seller,book);
                    boolean check = DB.updateBalance(user, wallet);
                    boolean checkSeller = DB.updateBalance(seller, seller_wallet);
                    if (check && checkSeller) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Toast.makeText(BookDetails.this, "Book bought successfully", Toast.LENGTH_SHORT).show();
                        intent.putExtra("username", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}