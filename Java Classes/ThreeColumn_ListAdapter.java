package com.learnandroid.loginsqlite;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter  extends ArrayAdapter<Trade> {


    private LayoutInflater mInflater;
    private ArrayList<Trade> trades;
    private  int mViewResourceId;


    public ThreeColumn_ListAdapter (Context context, int textViewResourceId,ArrayList<Trade> trades){
        super(context,textViewResourceId,trades);
        this.trades=trades;
        mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId =textViewResourceId;
    }
    public View getView (int position , View convertView , ViewGroup parents){
        convertView=mInflater.inflate(mViewResourceId,null);
        Trade trade = trades.get(position);

        if (trade != null){
            TextView code =(TextView) convertView.findViewById(R.id.textcode);
            TextView use =(TextView) convertView.findViewById(R.id.textuse);
            TextView book =(TextView) convertView.findViewById(R.id.textbook);


            if(code !=null){
                code.setText(trade.getCode());
            }
            if(use !=null){
                use.setText(trade.getUse());
            }

            if(book !=null){
                book.setText(trade.getBook());
            }

        }
          return  convertView;
    }
}
