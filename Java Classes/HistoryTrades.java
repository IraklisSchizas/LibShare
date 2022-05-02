package com.learnandroid.loginsqlite;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HistoryTrades extends AppCompatActivity {

    FloatingActionButton historytohome;
    DBHelper DB;
    ArrayList<Trade> historylist;
    Trade trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trades);

        historytohome =(FloatingActionButton) findViewById(R.id.home5);

        DB = new DBHelper(this);
        Intent intent=getIntent();
        final String user= intent.getStringExtra("username");

        historylist = new ArrayList<>();

        Cursor data = DB.getHistoryList(user);
        if(data.getCount() == 0) {
            Toast.makeText(HistoryTrades.this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        } else
        {
            while(data.moveToNext()){

                trade = new Trade (data.getString(0), data.getString(2), data.getString(3) );
                historylist.add(trade);

            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view,historylist);
            ListView  listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

        historytohome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username",user);
                startActivity(intent);
            }
        });
    }
}


