package com.learnandroid.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

//προσπάθεια να κάνω το floating button να δουλεύει και τελικά δουλεύει
    //δήλωση του button
    TextView message;
    EditText email, pass,postal,balanceTxt;
    Button editButton, buttonToHistory;
    DBHelper DB;
    FloatingActionButton home1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        postal = (EditText) findViewById(R.id.editTextTextPostalAddress);
        balanceTxt = (EditText) findViewById(R.id.BalanceText);
        message = (TextView) findViewById(R.id.Name);
        DB = new DBHelper(this);
        editButton = (Button) findViewById(R.id.edit);

        home1 =(FloatingActionButton) findViewById(R.id.home1);
        buttonToHistory =(Button) findViewById(R.id.history);

        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");
        message.setText(user+"'s Profile");

        //check αν υπαρχει τιμη αλλιως crash
        Boolean checkEmail = DB.checkEmail(user);
        if(checkEmail) {
            Cursor cursor = DB.getEmail(user);
            cursor.moveToFirst();
            email.setText(cursor.getString(0));
        }

        Boolean checkPostal = DB.checkPostal(user);
        if(checkPostal) {
            Cursor cursor2 = DB.getPostal(user);
            cursor2.moveToFirst();
            postal.setText(cursor2.getString(0));
        }

        Cursor cursor3 = DB.getPass(user);
        cursor3.moveToFirst();
        pass.setText(cursor3.getString(0));

        Boolean checkBalance = DB.checkBalance(user);
        if(checkBalance) {
            Cursor cursor4 = DB.getBalance(user);
            cursor4.moveToFirst();
            balanceTxt.setText(cursor4.getString(0));
        }

        //postal.setText(postal);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                String address = postal.getText().toString();
                String password = pass.getText().toString();
                String balance = balanceTxt.getText().toString();

                if(password.equals("")) {
                    Toast.makeText(ProfileActivity.this, "You need a password to enter the app", Toast.LENGTH_SHORT).show();
                }
                else {
                    //αν δεν υπαρχει ο χρηστης κανει insert αλλιως παει για update
                    Boolean checkUser = DB.checkProfile(user);
                    if(checkUser) {
                        User newUser  = new User(user, mail, address, Integer.getInteger(balance), password);
                        Boolean insert = DB.insertUser(newUser);
                        // λογικά δεν χρειάζεται το update
                        Boolean update = DB.updatePass(user, password);
                        if (insert && update) {
                            Toast.makeText(ProfileActivity.this, "Profile added successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Boolean update = DB.updateProfile(user, address, mail, balance);
                        Boolean update2 = DB.updatePass(user, password);
                        if (update && update2) {
                            Toast.makeText(ProfileActivity.this, "Profile update successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("username", user);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this, "Something went wrong with update", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });


        buttonToHistory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(ProfileActivity.this, HistoryTrades.class);
                 intent.putExtra("username",user);
                 startActivity(intent);
             }
         });

        home1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username",user);
            startActivity(intent);
            }

        });

        buttonToHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent = new Intent(getApplicationContext(), HistoryTrades.class);
                intent.putExtra("username",user);
                startActivity(intent);
            }

        });
    }
}