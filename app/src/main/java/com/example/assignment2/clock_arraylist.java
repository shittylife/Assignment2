package com.example.assignment2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class clock_arraylist extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> season;
    private Button btn_del_item;

    public clock_arraylist(Activity context,
                           ArrayList<String> season
    ) {
        super(context, R.layout.clock_listview, season);
        //inherit the information form super class

        //define the attributes of your class
        this.context = context;
        this.season = season;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.clock_listview, null,true);

        TextView tv_label = rowView.findViewById(R.id.tv_label);
        ImageView iv_icon = rowView.findViewById(R.id.Iv_Icon);

        tv_label.setText(season.get(position));

        //button for del
        btn_del_item = rowView.findViewById(R.id.btn_del_item);
        btn_del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season.remove(position);
                clock_arraylist.this.notifyDataSetChanged();//update immediately
            }
        });


        return rowView;
    }
}
