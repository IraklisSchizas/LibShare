package com.learnandroid.loginsqlite;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button register, signIn;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.btnsignup);
        signIn = (Button) findViewById(R.id.btnsignin);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();


                if (user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    ///////////////////////////////////////////////
                    if (user.equals("admin") )
                        Toast.makeText(MainActivity.this, "Registration failed,User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    else{
                        ////////////////////////////////////////////
                        if (pass.equals(repass)) {
                            Boolean checkUser = DB.checkUsername(user);
                            if (!checkUser) {
                                /*

                                Δημιουγούμε ένα νέο αντικείμενο user, με τα ορίσματα που πήραμε από το xml,
                                και το εισάγουμε στη βάση

                                */
                                User newUser = new User(user, null, null, 0 , pass);
                                Boolean insert = DB.insertUser(newUser);

                                // η παρακάτω εντολή δεν χρειάζεται
                                //Boolean insertprofile = DB.insertProfile(user, null, null, "0");
                                if (insert) {
                                    Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("username", user);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}