package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {


    FloatingActionButton searchToHome, add;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchToHome =(FloatingActionButton )findViewById(R.id.home3);
        add =(FloatingActionButton )findViewById(R.id.addbook);
        DB = new DBHelper(this);

        // get Data from SQLite
        final DBHelper myDb = new DBHelper(this);
        final String[] myData = myDb.SelectAllData();

        // autoCompleteTextView1
        final AutoCompleteTextView autoCom = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, myData);

        autoCom.setAdapter(adapter);
        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");



        //button for home
        searchToHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username",user);
                startActivity(intent);
            }

        });

        //button for details
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view) {

                String bookText = autoCom.getText().toString();
                Boolean checkbook = DB.checkBook(bookText);
                Boolean checkbalance= DB.checkBalance(user);
                if (checkbook && checkbalance) {
                    Intent intent = new Intent(getApplicationContext(), BookDetails.class);
                    intent.putExtra("username", user);
                    intent.putExtra("book", bookText);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SearchActivity.this, "Book does not exist or not enough balance", Toast.LENGTH_SHORT).show();
            }


        }
        });



    }

}