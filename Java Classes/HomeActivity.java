package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    //TextView textName;
    TextView message;
    Button profile, trade, search, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profile = (Button) findViewById(R.id.my_profile);
        trade = (Button) findViewById(R.id.trade_books);
        search = (Button) findViewById(R.id.search_books);
        logout = (Button) findViewById(R.id.logout);
        message = (TextView) findViewById(R.id.topmessage);


        //textView = findViewById(R.id.tv);
        //text View.setText("I want to remove text from here");

        //get the username from LoginActivity class
        Intent intent=getIntent();
        final String newString= intent.getStringExtra("username");
        message.setText("Welcome back: "+newString);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeActivity.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StartingPage1.class);
                startActivity(intent);
            }
        });


    }
}