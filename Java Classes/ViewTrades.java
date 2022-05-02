package com.learnandroid.loginsqlite;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewTrades extends AppCompatActivity {

    // creating variables for our array list,
    // db helper, adapter and recycler view.
    private ArrayList<TradeToReview> tradesArrayList;
    private DBHelper dbHelper;
    private TradeAdapter tradeAdapter;
    private RecyclerView RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trades);

        // initializing our all variables.
        tradesArrayList = new ArrayList<>();
        dbHelper = new DBHelper(ViewTrades.this);

        // getting our course array
        // list from db handler class.
        tradesArrayList = dbHelper.getTradesToReview();

        // on below line passing our array lost to our adapter class.
        tradeAdapter = new TradeAdapter(tradesArrayList, ViewTrades.this);
        RV = findViewById(R.id.idRVTrades);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewTrades.this, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        RV.setAdapter(tradeAdapter);
    }
}
