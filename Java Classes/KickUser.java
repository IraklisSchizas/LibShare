package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KickUser extends AppCompatActivity {
    FloatingActionButton home;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kick_user);

        home =(FloatingActionButton) findViewById(R.id.home7);

        DB = new DBHelper(this);
        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");
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