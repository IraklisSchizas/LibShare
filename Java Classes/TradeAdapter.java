package com.learnandroid.loginsqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.ViewHolder> {

        // variable for our array list and context
        private ArrayList<TradeToReview> tradesArrayList;
        private Context context;

        // constructor
        public TradeAdapter(ArrayList<TradeToReview> tradesArrayList, Context context) {
            this.tradesArrayList = tradesArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // on below line we are inflating our layout file for our recycler view items.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // on below line we are setting data
            // to our views of recycler view item.
            TradeToReview trade = tradesArrayList.get(position);
            holder.username.setText(trade.getTradeUsername());
            holder.bookName.setText(trade.getTradeBookName());
            holder.author.setText(trade.getTradeAuthor());
            holder.price.setText(trade.getTradePrice()+"");

            /*
             Ο παρακάτω κώδικας επιτρέπει στα trades να είναι clickable,
             έτσι ώστε να μπορούμε να τα κάνουμε στην συνέχεια accpet/reject
            */
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // on below line we are calling an intent.
                    Intent i = new Intent(context, AcceptOrRejectTrade.class);

                    // below we are passing all our values.
                    i.putExtra("username", trade.getTradeUsername());
                    i.putExtra("bookName", trade.getTradeBookName());
                    i.putExtra("author", trade.getTradeAuthor());
                    i.putExtra("price", trade.getTradePrice()+"");

                    // starting our activity.
                    context.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            // returning the size of our array list
            return tradesArrayList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            // creating variables for our text views.
            private TextView username, bookName, author, price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our text views
                username = itemView.findViewById(R.id.idUsername);
                bookName = itemView.findViewById(R.id.idBookName);
                author = itemView.findViewById(R.id.idAuthor);
                price = itemView.findViewById(R.id.idPrice);
            }
        }

}
