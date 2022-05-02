package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MenuAdmin extends AppCompatActivity {

    Button profile,tradeToReview, appEarnings, kickUser, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        profile = (Button) findViewById(R.id.myprofile);
        tradeToReview = (Button) findViewById(R.id.tradestoreview);
        appEarnings= (Button) findViewById(R.id.appearnings);
        kickUser= (Button) findViewById(R.id.kickuser);
        logout= (Button) findViewById(R.id.logout);

        //get the username from LoginActivity class
        Intent intent=getIntent();
        final String newString= intent.getStringExtra("username");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileAdmin.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        tradeToReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewTrades.class);
                // εναλακτικά:
                //Intent i = new Intent(MenuAdmin.this, ViewTrades.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        appEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppEarnings.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        kickUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), KickUser.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartingPage1.class);
                intent.putExtra("username",newString);
                startActivity(intent);
            }
        });
    }
}