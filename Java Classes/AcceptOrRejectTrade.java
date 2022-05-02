package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AcceptOrRejectTrade extends AppCompatActivity {

    // variables for our edit text, button, strings and db helper class.
    String usernameVar, bookNameVar, authorVar;
    int priceVar;
    private TextView usernameAOD, bookNameAOD, authorAOD, priceAOD;
    private Button acceptTradeBtn, deleteTradeBtn;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_reject_trade);

        // αρχικοποίηση μεταβλητών
        usernameAOD = findViewById(R.id.idUsernameAOD);
        bookNameAOD = findViewById(R.id.idBookNameAOD);
        authorAOD = findViewById(R.id.idAuthorAOD);
        priceAOD = findViewById(R.id.idPriceAOD);
        acceptTradeBtn = findViewById(R.id.idBtnAcceptTrade);
        deleteTradeBtn = findViewById(R.id.idBtnDeleteTrade);


        // on below line we are initialing our db helper class.
        db = new DBHelper(AcceptOrRejectTrade.this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        usernameVar = getIntent().getStringExtra("username");
        bookNameVar = getIntent().getStringExtra("bookName");
        authorVar = getIntent().getStringExtra("author");
        priceVar = Integer.parseInt(getIntent().getStringExtra("price"));


        // setting data to edit text
        // of our update activity.
        usernameAOD.setText(usernameVar);
        bookNameAOD.setText(bookNameVar);
        authorAOD.setText(authorVar);
        priceAOD.setText(priceVar+"");

        // adding on click listener to our update accept button.
        acceptTradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                db.acceptTrade(usernameVar, bookNameVar, authorVar, priceVar);

                // displaying a toast message that our course has been updated.
                Toast.makeText(AcceptOrRejectTrade.this, "Trade Accepted", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(AcceptOrRejectTrade.this, MenuAdmin.class);
                startActivity(i);
            }
        });

        // adding on click listener to our update delete button.
        deleteTradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                db.rejectTrade(usernameVar, bookNameVar, authorVar, priceVar);

                // displaying a toast message that our course has been updated.
                Toast.makeText(AcceptOrRejectTrade.this, "Trade Rejected", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(AcceptOrRejectTrade.this, MenuAdmin.class); // εδώ είχε MainActivity.class
                startActivity(i);
            }
        });
    }
}

