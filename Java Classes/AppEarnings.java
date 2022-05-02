package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppEarnings extends AppCompatActivity {

    private double appEarnings=0;
    private TextView appEar;
    FloatingActionButton home;
    DBHelper DB;


    public void increaseAppEarnings(double plus) {
        this.appEarnings += plus;
    }

    public double getTotalAppEarnings() {
        return this.appEarnings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_earnings);
        appEar = findViewById(R.id.idEarnings);
        home =(FloatingActionButton) findViewById(R.id.home6);

        DB = new DBHelper(this);
        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");

        appEar.setText(appEarnings+"");

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class);
                intent.putExtra("username",user);
                startActivity(intent);
            }

        });
    }
}